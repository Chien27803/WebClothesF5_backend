package com.javanc.service.impl;

import com.javanc.service.DashboardService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findCountUser() {
        String sql = new StringBuilder("select count(*) from User " +
                " inner join user_role ur on ur.user_id = user.id " +
                "inner join role r on r.id = ur.role_id " +
                "where r.name='user'").toString();
        Object result = entityManager
                .createNativeQuery(sql)
                .getSingleResult(); // Trả về Object

        return ((Number) result).longValue(); // Ép về Long
    }

    @Override
    public Long findCountOrders() {
        String sql = new StringBuilder("select count(*) from orders").toString();

        Object result = entityManager
                .createNativeQuery(sql)
                .getSingleResult(); // Trả về Object

        return ((Number) result).longValue(); // Ép về Long
    }

    @Override
    public Long findCountProducts() {
        String sql = new StringBuilder("select count(*) from product").toString();

        Object result = entityManager
                .createNativeQuery(sql)
                .getSingleResult(); // Trả về Object

        return ((Number) result).longValue(); // Ép về Long
    }

    @Override
    public List<Object[]> findTop5Recent() {
        String sql = new StringBuilder(" SELECT DISTINCT \n" +
                "            u.avatar, \n" +
                "            u.name, \n" +
                "            u.email, \n" +
                "            o.total, \n" +
                "            o.time \n" +  // <- Bắt buộc thêm dòng này nếu dùng ORDER BY o.time
                "        FROM orders o\n" +
                "        JOIN (\n" +
                "            SELECT user_id, MAX(time) AS max_time\n" +
                "            FROM orders\n" +
                "            WHERE status IN ('Hoàn thành')\n" +
                "            GROUP BY user_id\n" +
                "        ) latest ON o.user_id = latest.user_id AND o.time = latest.max_time\n" +
                "        JOIN user u ON o.user_id = u.id\n" +
                "        WHERE o.status IN ('Hoàn thành')\n" +
                "        ORDER BY o.time DESC\n" +
                "        LIMIT 5").toString();

        return entityManager
                .createNativeQuery(sql)
                .getResultList(); // Trả về List<Object[]>
    }

    @Override
    public List<Object[]> findTotal12Months() {
        String sql= new StringBuilder("SELECT \n" +
                "            MONTH(time) AS month,\n" +
                "            SUM(total) AS total_month\n" +
                "        FROM orders\n" +
                "        WHERE status IN ('Hoàn thành')\n" +
                "        GROUP BY MONTH(time)\n" +
                "        ORDER BY MONTH(time)").toString();
        return entityManager.createNativeQuery(sql).getResultList();
    }

//    @Override
//    public Long findTotalMonth(int month) {
//        String sql = new StringBuilder("SELECT SUM(total) AS sumTotal FROM orders WHERE MONTH(time) = :month").toString();
//        Object result = entityManager.createNativeQuery(sql)
//                .setParameter("month", month)
//                .getSingleResult();
//
//        return result != null ? ((Number) result).longValue() : 0L;
//
//
//
//    }

    @Override
    public List<Object[]> findTop5Orders() {
        String sql = new StringBuilder("SELECT \n" +
                "    p.id AS product_id,\n" +
                "    (\n" +
                "        SELECT i.src \n" +
                "        FROM image i \n" +
                "        WHERE i.product_id = p.id \n" +
                "        LIMIT 1\n" +
                "    ) AS src,\n" +
                "    p.name,\n" +
                "    p.description,\n" +
                "    p.price,\n" +
                "    SUM(d.sold_count) AS total_sold\n" +
                "FROM detail d\n" +
                "JOIN product p ON d.product_id = p.id\n" +
                "GROUP BY p.id, p.name, p.description, p.price\n" +
                "ORDER BY total_sold DESC\n" +
                "LIMIT 5;").toString();
        return entityManager
                .createNativeQuery(sql)
                .getResultList(); // Trả về List<Object[]>

    }

}
