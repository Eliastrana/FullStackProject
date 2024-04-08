describe('Play Quiz Test', () => {
  it('Should play be able to play a quiz', () => {

    cy.visit('http://localhost:5173/login');
    cy.get('input#username').type('user');
    cy.get('input#password').type('Password123');
    cy.get('button#sign-in').click();
    cy.url().should('include', '/');
    cy.wait(500);

    cy.visit('http://localhost:5173/MyAccount');
    cy.contains('Cypress Quiz Test').click();
    cy.get('.play-icon').eq(1).click();

    cy.contains('Paris').click();
    cy.contains('Quiz Completed!').should('be.visible');


  });
});