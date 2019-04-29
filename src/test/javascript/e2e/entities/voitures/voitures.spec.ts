import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { VoituresComponentsPage, VoituresUpdatePage } from './voitures.page-object';

describe('Voitures e2e test', () => {
    let navBarPage: NavBarPage;
    let voituresUpdatePage: VoituresUpdatePage;
    let voituresComponentsPage: VoituresComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Voitures', () => {
        navBarPage.goToEntity('voitures');
        voituresComponentsPage = new VoituresComponentsPage();
        expect(voituresComponentsPage.getTitle()).toMatch(/sTrackingApp.voitures.home.title/);
    });

    it('should load create Voitures page', () => {
        voituresComponentsPage.clickOnCreateButton();
        voituresUpdatePage = new VoituresUpdatePage();
        expect(voituresUpdatePage.getPageTitle()).toMatch(/sTrackingApp.voitures.home.createOrEditLabel/);
        voituresUpdatePage.cancel();
    });

    it('should create and save Voitures', () => {
        voituresComponentsPage.clickOnCreateButton();
        voituresUpdatePage.setPlaqueInput('plaque');
        expect(voituresUpdatePage.getPlaqueInput()).toMatch('plaque');
        voituresUpdatePage.setMarqueInput('marque');
        expect(voituresUpdatePage.getMarqueInput()).toMatch('marque');
        voituresUpdatePage.setCouleurInput('couleur');
        expect(voituresUpdatePage.getCouleurInput()).toMatch('couleur');
        voituresUpdatePage.devicesSelectLastOption();
        voituresUpdatePage.save();
        expect(voituresUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
