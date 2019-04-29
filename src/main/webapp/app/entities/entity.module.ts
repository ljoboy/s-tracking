import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { STrackingClientsModule } from './clients/clients.module';
import { STrackingDevicesModule } from './devices/devices.module';
import { STrackingVoituresModule } from './voitures/voitures.module';
import { STrackingHistoriquesModule } from './historiques/historiques.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        STrackingClientsModule,
        STrackingDevicesModule,
        STrackingVoituresModule,
        STrackingHistoriquesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class STrackingEntityModule {}
