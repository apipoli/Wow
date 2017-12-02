import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import { AppAdminModule } from '../../admin/admin.module';
import {
    MascotaService,
    MascotaPopupService,
    MascotaComponent,
    MisMascotasComponent,
    MascotaDetailComponent,
    MascotaDialogComponent,
    MascotaPopupComponent,
    MascotaDeletePopupComponent,
    MascotaDeleteDialogComponent,
    mascotaRoute,
    mascotaPopupRoute,
    MascotaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mascotaRoute,
    ...mascotaPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        AppAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MascotaComponent,
        MisMascotasComponent,
        MascotaDetailComponent,
        MascotaDialogComponent,
        MascotaDeleteDialogComponent,
        MascotaPopupComponent,
        MascotaDeletePopupComponent,
    ],
    entryComponents: [
        MascotaComponent,
        MisMascotasComponent,
        MascotaDialogComponent,
        MascotaPopupComponent,
        MascotaDeleteDialogComponent,
        MascotaDeletePopupComponent,
    ],
    providers: [
        MascotaService,
        MascotaPopupService,
        MascotaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppMascotaModule {}
