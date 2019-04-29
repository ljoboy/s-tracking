import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { DevicesComponentsPage, DevicesUpdatePage } from './devices.page-object';

describe('Devices e2e test', () => {
    let navBarPage: NavBarPage;
    let devicesUpdatePage: DevicesUpdatePage;
    let devicesComponentsPage: DevicesComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Devices', () => {
        navBarPage.goToEntity('devices');
        devicesComponentsPage = new DevicesComponentsPage();
        expect(devicesComponentsPage.getTitle()).toMatch(/sTrackingApp.devices.home.title/);
    });

    it('should load create Devices page', () => {
        devicesComponentsPage.clickOnCreateButton();
        devicesUpdatePage = new DevicesUpdatePage();
        expect(devicesUpdatePage.getPageTitle()).toMatch(/sTrackingApp.devices.home.createOrEditLabel/);
        devicesUpdatePage.cancel();
    });

    it('should create and save Devices', () => {
        devicesComponentsPage.clickOnCreateButton();
        devicesUpdatePage.setImeiInput('5');
        expect(devicesUpdatePage.getImeiInput()).toMatch('5');
        devicesUpdatePage.setNumeroSimInput('5');
        expect(devicesUpdatePage.getNumeroSimInput()).toMatch('5');
        devicesUpdatePage.clientsSelectLastOption();
        devicesUpdatePage.save();
        expect(devicesUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
