import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from '../../shared';
import { AppAdminModule } from '../../admin/admin.module';
import {
    PublicacionMascotaPerdidaService,
    PublicacionMascotaPerdidaPopupService,
    PublicacionMascotaPerdidaComponent,
    PublicacionMascotaPerdidaDetailComponent,
    PublicacionMascotaPerdidaDialogComponent,
    PublicacionMascotaPerdidaPopupComponent,
    PublicacionMascotaPerdidaDeletePopupComponent,
    PublicacionMascotaPerdidaDeleteDialogComponent,
    publicacionMascotaPerdidaRoute,
    publicacionMascotaPerdidaPopupRoute,
    PublicacionMascotaPerdidaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...publicacionMascotaPerdidaRoute,
    ...publicacionMascotaPerdidaPopupRoute,
];

@NgModule({
    imports: [
        AppSharedModule,
        AppAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PublicacionMascotaPerdidaComponent,
        PublicacionMascotaPerdidaDetailComponent,
        PublicacionMascotaPerdidaDialogComponent,
        PublicacionMascotaPerdidaDeleteDialogComponent,
        PublicacionMascotaPerdidaPopupComponent,
        PublicacionMascotaPerdidaDeletePopupComponent,
    ],
    entryComponents: [
        PublicacionMascotaPerdidaComponent,
        PublicacionMascotaPerdidaDialogComponent,
        PublicacionMascotaPerdidaPopupComponent,
        PublicacionMascotaPerdidaDeleteDialogComponent,
        PublicacionMascotaPerdidaDeletePopupComponent,
    ],
    providers: [
        PublicacionMascotaPerdidaService,
        PublicacionMascotaPerdidaPopupService,
        PublicacionMascotaPerdidaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppPublicacionMascotaPerdidaModule {}
