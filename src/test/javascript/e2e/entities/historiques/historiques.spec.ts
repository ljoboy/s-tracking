import { browser, protractor } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { HistoriquesComponentsPage, HistoriquesUpdatePage } from './historiques.page-object';

describe('Historiques e2e test', () => {
    let navBarPage: NavBarPage;
    let historiquesUpdatePage: HistoriquesUpdatePage;
    let historiquesComponentsPage: HistoriquesComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Historiques', () => {
        navBarPage.goToEntity('historiques');
        historiquesComponentsPage = new HistoriquesComponentsPage();
        expect(historiquesComponentsPage.getTitle()).toMatch(/sTrackingApp.historiques.home.title/);
    });

    it('should load create Historiques page', () => {
        historiquesComponentsPage.clickOnCreateButton();
        historiquesUpdatePage = new HistoriquesUpdatePage();
        expect(historiquesUpdatePage.getPageTitle()).toMatch(/sTrackingApp.historiques.home.createOrEditLabel/);
        historiquesUpdatePage.cancel();
    });

    it('should create and save Historiques', () => {
        historiquesComponentsPage.clickOnCreateButton();
        historiquesUpdatePage.setDateHistoriqueInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(historiquesUpdatePage.getDateHistoriqueInput()).toContain('2001-01-01T02:30');
        historiquesUpdatePage.setLongitudeInput('5');
        expect(historiquesUpdatePage.getLongitudeInput()).toMatch('5');
        historiquesUpdatePage.setLatitudeInput('5');
        expect(historiquesUpdatePage.getLatitudeInput()).toMatch('5');
        historiquesUpdatePage.devicesSelectLastOption();
        historiquesUpdatePage.save();
        expect(historiquesUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
