describe('User Registration', () => {
  it('should enable "register" button when all fields are filled', () => {
    cy.visit('/create-user');

    cy.get('input[id="username"]').type('TestUser');
    cy.get('input[id="email"]').type('TestUser@test.com');
    cy.get('input[id="password"]').type('test123');
    cy.get('input[id="password-confirmation"]').type('test123');

    cy.get('button[id="register-user"]').should('not.be.disabled');
  });

  it('should disable "register" button when username is empty', () => {
    cy.visit('/create-user');

    cy.get('input[id="email"]').type('user@test.com');
    cy.get('input[id="password"]').type('password1');
    cy.get('input[id="password-confirmation"]').type('password1');

    cy.get('button[id="register-user"]').should('be.disabled');

  });

  it('should disable "register" button when email is empty', () => {
    cy.visit('/create-user');

    cy.get('input[id="username"]').type('TestUser');
    cy.get('input[id="password"]').type('password1');
    cy.get('input[id="password-confirmation"]').type('password1');

    cy.get('button[id="register-user"]').should('be.disabled');
  });

  it('should disable "register" button when password is empty', () => {
    cy.visit('/create-user');

    cy.get('input[id="username"]').type('TestUser');
    cy.get('input[id="email"]').type('user@test.com');
    cy.get('input[id="password-confirmation"]').type('password1');

    cy.get('button[id="register-user"]').should('be.disabled');

  });

  it('validates that the password is at least 8 characters long', () => {
    cy.visit('/create-user');

    cy.get('input[id="username"]').type('TestUser');
    cy.get('input[id="email"]').type('test@user.com');

    cy.get('input[id="password"]').type('pass1');
    cy.get('input[id="password-confirmation"]').type('pass1');

    cy.get('form').submit();
    cy.contains('Password must be at least 8 characters long and include a letter and a number.');
  });

  it('validates that the password includes letters and number', () => {
    cy.visit('/create-user');

    cy.get('input[id="username"]').type('TestUser');
    cy.get('input[id="email"]').type('test@user.com');

    cy.get('input[id="password"]').type('userPassword');
    cy.get('input[id="password-confirmation"]').type('userPassword');

    cy.get('form').submit();
    cy.contains('Password must be at least 8 characters long and include a letter and a number.');
  });

  it('validates passwords match before submitting form', () => {
    cy.visit('/create-user');


    cy.get('input[id="username"]').type('TestUser');
    cy.get('input[id="email"]').type('TestUser@test.com');
    cy.get('input[id="password"]').type('password123');
    cy.get('input[id="password-confirmation"]').type('otherpassword');

    cy.get('form').submit();
    cy.contains('Passwords do not match.');
  });

  it('should redirect user to home page on successful registration', () => {
    cy.visit('/create-user');

    cy.get('input[id="username"]').type('TestUser');
    cy.get('input[id="email"]').type('test@user.com');
    cy.get('input[id="password"]').type('password1');
    cy.get('input[id="password-confirmation"]').type('password1');

    cy.get('form').submit();
    cy.url().should('include', '/');
  });
});
