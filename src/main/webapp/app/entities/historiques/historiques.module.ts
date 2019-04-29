import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { STrackingSharedModule } from 'app/shared';
import {
    HistoriquesComponent,
    HistoriquesDetailComponent,
    HistoriquesUpdateComponent,
    HistoriquesDeletePopupComponent,
    HistoriquesDeleteDialogComponent,
    historiquesRoute,
    historiquesPopupRoute
} from './';

const ENTITY_STATES = [...historiquesRoute, ...historiquesPopupRoute];

@NgModule({
    imports: [STrackingSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        HistoriquesComponent,
        HistoriquesDetailComponent,
        HistoriquesUpdateComponent,
        HistoriquesDeleteDialogComponent,
        HistoriquesDeletePopupComponent
    ],
    entryComponents: [HistoriquesComponent, HistoriquesUpdateComponent, HistoriquesDeleteDialogComponent, HistoriquesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class STrackingHistoriquesModule {}
