package org.example.transactionservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactionservice.models.Category;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.models.TransactionType;
import org.example.transactionservice.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(Long userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);
        fillTransactionsData(categories);

        return categories;
    }

    public Category getCategoryById(Long id, Long userId) {
        Category category = categoryRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new RuntimeException("Category not found"));
        fillTransactionData(category);

        return category;
    }

    public void deleteCategoryById(Long id, Long userId) {
        categoryRepository.deleteByIdAndUserId(id, userId);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    private void fillTransactionsData(List<Category> categories) {
        categories.forEach(this::fillTransactionData);
    }

    private void fillTransactionData(Category category) {
        Set<Transaction> transactions = category.getTransactions();
        log.info("Found {} transactions", transactions.size());

        for (Transaction transaction : transactions) {
            log.info(transaction.getTitle());
        }

        LocalDate today = LocalDate.now();
        YearMonth currentMonth = YearMonth.from(today);
        Year currentYear = Year.from(today);

        log.info("Current day: {}", today);
        log.info("Current month: {}", currentMonth);

        double dayIncome = 0.0, dayOutcome = 0.0,
                monthIncome = 0.0, monthOutcome = 0.0,
                yearIncome = 0.0, yearOutcome = 0.0;

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTime().toLocalDate();

            log.info("Transaction {} ({}) date: {}", transaction.getId(), transaction.getValue() , transactionDate);

            if (transactionDate.equals(today)) {
                if (transaction.getType() == TransactionType.INCOME) {
                    dayIncome += transaction.getValue();
                } else if (transaction.getType() == TransactionType.OUTCOME) {
                    dayOutcome += transaction.getValue();
                }
            }

            if (YearMonth.from(transactionDate).equals(currentMonth)) {
                if (transaction.getType() == TransactionType.INCOME) {
                    monthIncome += transaction.getValue();
                } else if (transaction.getType() == TransactionType.OUTCOME) {
                    monthOutcome += transaction.getValue();
                }
            }

            if (Year.from(transactionDate).equals(currentYear)) {
                if (transaction.getType() == TransactionType.INCOME) {
                    yearIncome += transaction.getValue();
                } else if (transaction.getType() == TransactionType.OUTCOME) {
                    yearOutcome += transaction.getValue();
                }
            }
        }

        log.info("Data:\n\tday income: {}\n\tday outcome: {}\n\tmonth income{}\n\tmonth outcome{}\n", dayIncome, dayOutcome, monthIncome, monthOutcome);

        category.setDayIncome(dayIncome);
        category.setDayOutcome(dayOutcome);
        category.setMonthIncome(monthIncome);
        category.setMonthOutcome(monthOutcome);
        category.setYearIncome(yearIncome);
        category.setYearOutcome(yearOutcome);
    }
}
