import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ClientsComponentsPage, ClientsUpdatePage } from './clients.page-object';

describe('Clients e2e test', () => {
    let navBarPage: NavBarPage;
    let clientsUpdatePage: ClientsUpdatePage;
    let clientsComponentsPage: ClientsComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Clients', () => {
        navBarPage.goToEntity('clients');
        clientsComponentsPage = new ClientsComponentsPage();
        expect(clientsComponentsPage.getTitle()).toMatch(/sTrackingApp.clients.home.title/);
    });

    it('should load create Clients page', () => {
        clientsComponentsPage.clickOnCreateButton();
        clientsUpdatePage = new ClientsUpdatePage();
        expect(clientsUpdatePage.getPageTitle()).toMatch(/sTrackingApp.clients.home.createOrEditLabel/);
        clientsUpdatePage.cancel();
    });

    it('should create and save Clients', () => {
        clientsComponentsPage.clickOnCreateButton();
        clientsUpdatePage.setNomInput('nom');
        expect(clientsUpdatePage.getNomInput()).toMatch('nom');
        clientsUpdatePage.setDateAbonnementInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(clientsUpdatePage.getDateAbonnementInput()).toContain('2001-01-01T02:30');
        clientsUpdatePage.setProvinceInput('province');
        expect(clientsUpdatePage.getProvinceInput()).toMatch('province');
        clientsUpdatePage.setVilleInput('ville');
        expect(clientsUpdatePage.getVilleInput()).toMatch('ville');
        clientsUpdatePage.setAdresseInput('adresse');
        expect(clientsUpdatePage.getAdresseInput()).toMatch('adresse');
        clientsUpdatePage.setPasswordInput('password');
        expect(clientsUpdatePage.getPasswordInput()).toMatch('password');
        clientsUpdatePage.typesSelectLastOption();
        clientsUpdatePage.setTelephoneInput('5');
        expect(clientsUpdatePage.getTelephoneInput()).toMatch('5');
        clientsUpdatePage.setEmailInput('email');
        expect(clientsUpdatePage.getEmailInput()).toMatch('email');
        clientsUpdatePage.setRccmInput('rccm');
        expect(clientsUpdatePage.getRccmInput()).toMatch('rccm');
        clientsUpdatePage.save();
        expect(clientsUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
