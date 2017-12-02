import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import {
    DistritoService,
    DistritoPopupService,
    DistritoComponent,
    DistritoDetailComponent,
    DistritoDialogComponent,
    DistritoPopupComponent,
    DistritoDeletePopupComponent,
    DistritoDeleteDialogComponent,
    distritoRoute,
    distritoPopupRoute,
    DistritoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...distritoRoute,
    ...distritoPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DistritoComponent,
        DistritoDetailComponent,
        DistritoDialogComponent,
        DistritoDeleteDialogComponent,
        DistritoPopupComponent,
        DistritoDeletePopupComponent,
    ],
    entryComponents: [
        DistritoComponent,
        DistritoDialogComponent,
        DistritoPopupComponent,
        DistritoDeleteDialogComponent,
        DistritoDeletePopupComponent,
    ],
    providers: [
        DistritoService,
        DistritoPopupService,
        DistritoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppDistritoModule {}
