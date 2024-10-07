package com.bobocode.web.controller;

import com.bobocode.dao.AccountDao;
import com.bobocode.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that handles requests with url "/accounts"
 */
@RestController
@RequestMapping("/Account")
public class AccountRestController {

  // Inject AccountDao implementation
  private final AccountDao accountDao;

  @Autowired
  public AccountRestController(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  // Handle GET request and return a list of accounts
  @GetMapping
  public List<Account> getAllAccounts() {
    return accountDao.findAll();
  }

  // Handle GET request with id as path variable and return account by id
  @GetMapping("/{id}")
  public Account getAccountById(@PathVariable Long id) {
    return accountDao.findById(id);
  }

  // Handle POST request, receive account as request body, save account and return it
  // Configure HTTP response status code 201 - CREATED
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Account createAccount(@RequestBody Account account) {
    accountDao.save(account);
    return account;
  }

  // Handle PUT request with id as path variable and receive account as request body
  // Check if account id and path variable are the same and throw IllegalStateException otherwise
  // Configure HTTP response status code 204 - NO CONTENT
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateAccount(@PathVariable Long id, @RequestBody Account account) {
    if (!account.getId().equals(id)) {
      throw new IllegalStateException("Account ID in the path does not match the account ID in the request body");
    }
    accountDao.save(account);
  }

  // Handle DELETE request with id as path variable, remove account by id
  // Configure HTTP response status code 204 - NO CONTENT
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteAccount(@PathVariable Long id) {
    accountDao.remove(getAccountById(id));
  }
}
