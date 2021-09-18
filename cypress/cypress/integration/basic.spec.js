describe('Prueba local', () => {

    it('Login', () => {
        cy.visit('http://localhost:8010')
        cy.get('#mat-input-0').clear().type('admin')
        cy.get('.mat-raised-button').click()
        cy.get('h1').should('contain', 'About')
    });

    it('Logout', () => {
        cy.visit('http://localhost:8010')
        cy.get('#mat-input-0').clear().type('admin')
        cy.get('.mat-raised-button').click()
        cy.get(':nth-child(4) > .toolbar-item').click()
        cy.get('.mat-button-wrapper').should('contain', 'Login')
    });

});