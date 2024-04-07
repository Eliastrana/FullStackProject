describe('Play Quiz Test', () => {
  it('Should play be able to play a quiz', () => {

    cy.visit('http://localhost:5173/login');
    cy.get('input#username').type('testbruker');
    cy.get('input#password').type('password1');
    cy.get('button#sign-in').click();
    cy.url().should('include', '/MyAccount');

    cy.visit('http://localhost:5173/Create');

    cy.contains('Cypress Quiz Test').click();

    cy.contains('Start').click();
    cy.contains('Paris').click();
    cy.contains('Quiz Completed!').should('be.visible');


  });
});