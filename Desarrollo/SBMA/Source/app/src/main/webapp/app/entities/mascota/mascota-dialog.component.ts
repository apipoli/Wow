import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Mascota } from './mascota.model';
import { MascotaPopupService } from './mascota-popup.service';
import { MascotaService } from './mascota.service';
import { Raza, RazaService } from '../raza';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mascota-dialog',
    templateUrl: './mascota-dialog.component.html'
})
export class MascotaDialogComponent implements OnInit {

    mascota: Mascota;
    isSaving: boolean;

    razas: Raza[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mascotaService: MascotaService,
        private razaService: RazaService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.razaService.query()
            .subscribe((res: ResponseWrapper) => { this.razas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mascota.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mascotaService.update(this.mascota));
        } else {
            this.subscribeToSaveResponse(
                this.mascotaService.create(this.mascota));
        }
    }

    private subscribeToSaveResponse(result: Observable<Mascota>) {
        result.subscribe((res: Mascota) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Mascota) {
        this.eventManager.broadcast({ name: 'mascotaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackRazaById(index: number, item: Raza) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mascota-popup',
    template: ''
})
export class MascotaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mascotaPopupService: MascotaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mascotaPopupService
                    .open(MascotaDialogComponent as Component, params['id']);
            } else {
                this.mascotaPopupService
                    .open(MascotaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
