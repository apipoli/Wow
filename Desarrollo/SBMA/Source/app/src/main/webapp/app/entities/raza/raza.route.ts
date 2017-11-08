import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RazaComponent } from './raza.component';
import { RazaDetailComponent } from './raza-detail.component';
import { RazaPopupComponent } from './raza-dialog.component';
import { RazaDeletePopupComponent } from './raza-delete-dialog.component';

@Injectable()
export class RazaResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const razaRoute: Routes = [
    {
        path: 'raza',
        component: RazaComponent,
        resolve: {
            'pagingParams': RazaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Razas'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'raza/:id',
        component: RazaDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Razas'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const razaPopupRoute: Routes = [
    {
        path: 'raza-new',
        component: RazaPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Razas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'raza/:id/edit',
        component: RazaPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Razas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'raza/:id/delete',
        component: RazaDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Razas'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
