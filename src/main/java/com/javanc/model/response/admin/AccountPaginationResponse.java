package com.javanc.model.response.admin;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountPaginationResponse {
    List<AccountAdminResponse> accounts;
    Long totalAccounts;
    Long limitAccount;
}
