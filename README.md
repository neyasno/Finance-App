# Finance-App

### Table of contents

- [Technologies](#technological-stack)
- [Requirements](#requirements)
- Microservice Architecture
  - [Services description](#microsevice-arhitecture)
  - [Services relationship scheme](#services-relationship)
- [Database](#database-models)
- [API](#api)
- [Frontend pages](#frontend-pages)

---

### Technological stack

 - Frontend :
    - React , Redux-Toolkit 
    - NextJs , Next-Intl , Next-Themes
    - Tailwind 

- Backend : 
    - Spring-Boot
    - Spring Cloud (Eureka)
    - Spring Security + JWT 
    - Spring Data JPA + PostgreSQL
    - Docker
    - Docker Compose
    - Kafka (optional)

- Mobile : 
    - Capacitor

### Requirements

- Backend
    1. Microservice arhitecture

- Mobile
    1. User authentication and secure login.
    2. Add, edit, and delete transactions (income & expenses).
    3. Generate monthly expense reports with charts.
    4. Set budget limits and receive alerts when exceeding limits.
    5. Categorization of transactions (food, transport, rent, etc.).
    6. Cloud backup and synchronization across multiple devices.
    7. Export reports to CSV or PDF.

### Microsevice Arhitecture

>_Income service request_  <-( _service_ ) \
>_Outcome service request_  ( _service_ ) -> 

>_Income service message_ <<<✉ _service_  
>_Outcome service message_ ✉>>> _service_  

- User Service
    1. Create User  <-( Auth-Service )
    2. Update User <-( Auth-Service ) 
    3. Get User
    
- Auth Service 
    1. Register user ->(User Service)
    2. Login User
    3. Verify token
    4. Password Change Req -> (Notification Service)
    5. Password Change -> (User Service)

- Transaction Service
    1. Create Transaction ✉>>> Budget-Service
    2. Update Transaction ✉>>> Budget-Service
    3. Delete Transaction ✉>>> Budget-Service
    4. Get One Transaction
    5. Get All Transactions (Pagination)

- Constraints Service
    1. Get Constraint 
    2. Create Constraint
    3. Update Constraint 
    4. Delete Constraint
    5. Update Constrain-Limit 

       <<<✉ Transaction-Service  
      - >>>✉ Notification-Service

- Notification Service 
    1. Send Email Notification for password change <-(Auth Service)
    2. Send Email Notification about budget limit <<<✉ Transaction-Service  
    3. Send Push Notification for budget limit <<<✉ Transaction-Service 
    4. Send WebSocket Notification  <<<✉ Transaction-Service 
    

- Financial Analitics Servise
    1. Create raw report (CSV) <- ( Transaction-Service )
    3. Get analitics data for diagrams. (...)

- Statistic servise
    1. Get budget
    2. Get day , month , year , alltime - income/outcome
    3. Get day , month , year , alltime for category

### Services Relationship

![services relationships](docs/services_realtionship.jpg)


### Database Models

    User :
        - id 
        - email
        - password 

    Transaction :
        - id
        - title
        - type ('income' | 'outcome')
        - value
        - time
        - category

    Constraint : 
        - id
        - value  
        - available (value - moment_outcome)
        - time

    Category :
        - id
        - title

[//]: # (    Budget :)
[//]: # (        - id)
[//]: # (        - budget_value &#40;available money&#41;)


### API 

 
- User Service
    1. Create User
        > POST /api/users
    2. Update User  
        > PUT /api/users/:id
    3. Get User
        > GET /api/users/:id
    
- Auth Service 
    1. Register user 
        > POST /api/auth/registration
    2. Login User
        > POST /api/auth/login
    3. Password Change Request
        > POST /api/auth/recovery/request
    4. Password Change
        > POST /api/auth/recovery/confirmation
    5. Logout User  
        > POST /api/auth/logout
    6. Refresh Token  
        > POST /api/auth/refresh
       
- Transaction Service
    1. Create Transaction 
        > POST /api/transactions
    2. Update Transaction 
        > PUT /api/transactions/:id
    3. Delete Transaction 
        > DELETE /api/transactions/:id
    4. Get One Transaction
        > GET /api/transactions/:id
    5. Get All Transactions (Pagination)
        > GET /api/transactions?page=x&size=x
    
- Transaction Service (Categories)
    1. Get One Category by ID
        > GET /api/categories/:categoryId
    2. Get All Categories
        > GET /api/categories
    3. Create Category
        > POST /api/categories
    4. Update Category
        > PUT /api/categories/:categoryId
    5. Delete Category
        > DELETE /api/category/:categoryId
        

- Statistics service
    1. Get budget
        > GET ///
    2. Get all statistic (day , month , year , alltime) , income / outcome
    3. Get category statistic

- Constraints Service
    1. Get Constraint 
        > GET /api/constraints/:constraintId
    2. Get All Constraints
        > GET /api/constraints
    3. Create Constraint
        > POST /api/constraints
    4. Update Constraint 
        > PUT /api/constraints/:constraintId
    5. Delete Constraint
        > DELETE /api/constraints/:constraintId

- Notification Service 
    1. Send Email Notification for password change 
        > POST /api/notifications/password-change
    2. Send Email Notification about budget limit 
        > POST /api/notifications/budget-limit  
    3. Send Push Notification for budget limit 
        > POST /api/notifications/budget-limit  
     4. Send WebSocket Notification  
        > WS /api/notifications/ws

- Financial Analitics Servise
    1. Create raw report (CSV)
        > GET /api/analytics/csv
    2. Create diagrams report (PDF)
        > GET /api/analytics/pdf

### Frontend Pages

- Layout
    - Header
        - Navigation
            - Analitics
        - Button-Bar
            - Change-Locale-Button
            - Change-Theme-Button
            - Logout-Button

- Modal Windows

- Verification Page
    - Login Page
        - Login Form
    - Registration Page
        - Registration Form
    - Restore Password Req
        - Email Form
    - Restore Password
        - Restore Password Form

- Home Page
    - Section
        - Current Budget Brick
        - Constraint-Block
            - Add-Constraints-Button
        - Categories Bricks
    - Aside
        - Transaction History
        - Add-Transaction-Button

- Analitics Page
    - Generate-CSV-Button
    - Generate-PDF-Button
    - Categories Bricks:
        - General Brick
        - Income Brick
        - Outcome Brick
        - Categories brick


```

├── app
|  ├── api
|  |  └── export-transactions
|  |     └── route.ts
|  ├── globals.css
|  ├── layout.tsx
|  ├── [locale]
|  |  ├── analitics
|  |  |  └── page.tsx
|  |  ├── layout.tsx
|  |  ├── page.tsx
|  |  └── verification
|  |     ├── layout.tsx
|  |     ├── login
|  |     |  └── page.tsx
|  |     ├── registration
|  |     |  └── page.tsx
|  |     ├── restore_password
|  |     |  └── page.tsx
|  |     └── restore_password_req
|  |        └── page.tsx
|  └── _components
|     ├── common
|     |  ├── buttons
|     |  |  ├── Button.tsx
|     |  |  └── DeleteButton.tsx
|     |  ├── ContsraintValue.tsx
|     |  ├── inputs
|     |  |  ├── NumberInput.tsx
|     |  |  ├── Select.tsx
|     |  |  └── TextInput.tsx
|     |  ├── Loading.tsx
|     |  └── MoneyValue.tsx
|     ├── layout
|     |  ├── Header
|     |  |  ├── AppTitle.tsx
|     |  |  ├── Button-Bar
|     |  |  |  ├── ButtonBar.tsx
|     |  |  |  └── buttons
|     |  |  |     ├── LocaleButton.tsx
|     |  |  |     ├── LogoutButton.tsx
|     |  |  |     └── ThemeButton.tsx
|     |  |  ├── Header.tsx
|     |  |  └── Navigation
|     |  |     ├── links
|     |  |     |  └── AnaliticsLink.tsx
|     |  |     └── Navigation.tsx
|     |  ├── Modal
|     |  |  ├── Modal.tsx
|     |  |  ├── ModalLayout.tsx
|     |  |  └── modals
|     |  |     ├── common
|     |  |     |  ├── categories
|     |  |     |  |  ├── ChangeCategoryForm.tsx
|     |  |     |  |  └── CreateCategoryForm.tsx
|     |  |     |  ├── constraints
|     |  |     |  |  ├── ChangeConstraintForm.tsx
|     |  |     |  |  ├── ConstraintCategorySelector.tsx
|     |  |     |  |  └── CreateConstraintForm.tsx
|     |  |     |  ├── suggestions
|     |  |     |  |  ├── DeleteSuggestion.tsx
|     |  |     |  |  └── ExitSuggestion.tsx
|     |  |     |  └── transactions
|     |  |     |     ├── CategorySelector.tsx
|     |  |     |     ├── ChangeTransactionForm.tsx
|     |  |     |     ├── CreateTransactionForm.tsx
|     |  |     |     ├── TransactionForm.tsx
|     |  |     |     └── TypeSelector.tsx
|     |  |     └── Overviews
|     |  |        ├── CategoryOverview
|     |  |        |  ├── buttons
|     |  |        |  |  ├── ChangeCategoryButton.tsx
|     |  |        |  |  └── DeleteCategoryButton.tsx
|     |  |        |  └── CategoryOverview.tsx
|     |  |        ├── ConstraintsOverview
|     |  |        |  ├── Constraint.tsx
|     |  |        |  └── ConstraintsOverwiev.tsx
|     |  |        └── TransactionOverview
|     |  |           ├── buttons
|     |  |           |  ├── ChangeTransactionButton.tsx
|     |  |           |  └── DeleteTransactionButton.tsx
|     |  |           └── TransactionOverview.tsx
|     |  ├── ThemesProvider
|     |  |  └── ThemesProvider.tsx
|     |  └── VerificationWrapper
|     |     └── VerificationWrapper.tsx
|     └── pages
|        ├── analitics
|        |  ├── AnaliticsPage.tsx
|        |  ├── buttons
|        |  |  ├── GenerateCSVButton.tsx
|        |  |  └── GeneratePDFButton.tsx
|        |  └── section
|        |     ├── AnaliticsContent.tsx
|        |     └── StatisticBricks
|        |        ├── AllIncomeBrick.tsx
|        |        ├── AllOutcomeBrick.tsx
|        |        ├── CategoriesBrick.tsx
|        |        ├── GeneralBrick.tsx
|        |        └── StatisticBricks.tsx
|        ├── home
|        |  ├── aside
|        |  |  ├── AddTransactionButton
|        |  |  |  └── AddTransactionButton.tsx
|        |  |  ├── ContentAside.tsx
|        |  |  └── TransactionHistory
|        |  |     ├── TransactionContainer
|        |  |     |  ├── Transaction.tsx
|        |  |     |  └── TransactionContainer.tsx
|        |  |     └── TransactionHistory.tsx
|        |  ├── ContentPage.tsx
|        |  ├── HomePage.tsx
|        |  ├── section
|        |  |  ├── Budget
|        |  |  |  ├── Budget.tsx
|        |  |  |  └── Statistic
|        |  |  |     └── PeriodStatistic.tsx
|        |  |  ├── Category
|        |  |  |  ├── Categories.tsx
|        |  |  |  ├── CategoriesContainer.tsx
|        |  |  |  ├── CategoryBrick.tsx
|        |  |  |  └── CreateCategoryButton.tsx
|        |  |  ├── Constraint
|        |  |  |  ├── AddConstraintButton.tsx
|        |  |  |  └── Constraints.tsx
|        |  |  └── ContentSection.tsx
|        |  └── UnloginedPage.tsx
|        └── verification
|           ├── login
|           |  └── LoginForm.tsx
|           ├── registration
|           |  └── RegistrationForm.tsx
|           ├── restore_password
|           |  └── RestorePasswordForm.tsx
|           └── restore_password_req
|              └── RestorePasswordReqForm.tsx
├── enums.ts
├── i18n
|  ├── request.ts
|  └── routing.ts
├── middleware.ts
├── store
|  ├── ReduxProvider.tsx
|  ├── slices
|  |  ├── dataActualitySlice.ts
|  |  ├── modalSlice.ts
|  |  └── userSlice.ts
|  └── store.ts
└── utils
   ├── budgetCalculator.ts
   ├── calculator.ts
   ├── cookie.ts
   ├── fetchApi.ts
   └── hooks
      ├── useCategoryOverview.ts
      ├── useConstraintOverview.ts
      ├── useLogin.ts
      ├── useModal.ts
      └── useTransactionOverview.ts

```        



