import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DistritoComponent } from './distrito.component';
import { DistritoDetailComponent } from './distrito-detail.component';
import { DistritoPopupComponent } from './distrito-dialog.component';
import { DistritoDeletePopupComponent } from './distrito-delete-dialog.component';

@Injectable()
export class DistritoResolvePagingParams implements Resolve<any> {

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

export const distritoRoute: Routes = [
    {
        path: 'distrito',
        component: DistritoComponent,
        resolve: {
            'pagingParams': DistritoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Distritos'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'distrito/:id',
        component: DistritoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Distritos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const distritoPopupRoute: Routes = [
    {
        path: 'distrito-new',
        component: DistritoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Distritos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'distrito/:id/edit',
        component: DistritoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Distritos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'distrito/:id/delete',
        component: DistritoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Distritos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
