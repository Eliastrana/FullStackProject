describe('Login Validation', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4173/Login',)
  });
  it('login button should not be disabled when input fields are filled', () => {
    cy.get('input[id="username"]').type('user');
    cy.get('input[id="password"]').type('Password123');

    cy.get('button[id="sign-in"]').should('not.be.disabled');
  });

  it('login button should be disabled when input is empty', () => {

    cy.get('button[id="sign-in"]').should('be.disabled');
  });

  it('login button should be disabled when username is empty', () => {
    cy.get('input[id="password"]').type('Password123');
    cy.get('button[id="sign-in"]').should('be.disabled');
  });

  it('login button should be disabled when password is empty', () => {
    cy.get('input[id="username"]').type('user');
    cy.get('button[id="sign-in"]').should('be.disabled');
  });

  it('displays an error message for wrong username or password', () => {
    cy.visit('/login');
    cy.get('input[id="username"]').type('feilbruker');
    cy.get('input[id="password"]').type('feilpassord');
    cy.get('button[id="sign-in"]').click();
    cy.contains('Incorrect username or password.').should('be.visible');
  });

  it('redirects user to the MyAccount page on successful login', () => {
    cy.visit('/login');

    cy.get('input[type="text"]').type('user');
    cy.get('input[type="password"]').type('Password123');
    cy.get('form').submit();
    
    cy.url().should('include', '/');

  });

});