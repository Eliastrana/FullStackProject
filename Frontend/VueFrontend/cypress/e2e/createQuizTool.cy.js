describe('Create Quiz Tool Test', () => {
  it('Allows a user to log in and directly access Create Quiz Tool', () => {
    cy.visit('http://localhost:5173/login');

    cy.get('input#username').type('test');
    cy.get('input#password').type('test');
    cy.get('button#sign-in').click();

    cy.url().should('include', '/MyAccount');

    cy.visit('http://localhost:5173/QuizcreatorTool');

    cy.url().should('include', '/QuizcreatorTool');

    cy.get('#quiz-title-input').type('cypress Quiz test');
    cy.get('textarea').first().type('This is a cypress test quiz');
    cy.get('select').eq(1).select('Easy');

    cy.get('.quiz-type-buttons button').first().click();
    cy.get('.question-input').type('What is the capital of France?');
    cy.get('.answer-group').eq(0).find('.question-answer').type('Paris');
    cy.get('.answer-group').eq(0).find('.question-checkbox').check();
    cy.get('#add-answers').click();
    cy.get('.answer-group').eq(1).find('.question-answer').type('London');
    cy.get('#add-answers').click();
    cy.get('.answer-group').eq(2).find('.question-answer').type('Oslo');
    cy.get('#add-answers').click();
    cy.get('.answer-group').eq(3).find('.question-answer').type('Rome');

    cy.get('button.compileButton').click();




  });
});