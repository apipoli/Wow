import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import {EstadoMascotaPerdida, PublicacionMascotaPerdida} from './publicacion-mascota-perdida.model';
import { PublicacionMascotaPerdidaPopupService } from './publicacion-mascota-perdida-popup.service';
import { PublicacionMascotaPerdidaService } from './publicacion-mascota-perdida.service';
import { User, UserService } from '../../shared';
import { Distrito, DistritoService } from '../distrito';
import { Mascota, MascotaService } from '../mascota';
import { ResponseWrapper } from '../../shared';
import {Principal} from '../../shared/auth/principal.service';

@Component({
    selector: 'jhi-publicacion-mascota-perdida-dialog',
    templateUrl: './publicacion-mascota-perdida-dialog.component.html'
})
export class PublicacionMascotaPerdidaDialogComponent implements OnInit {

    publicacionMascotaPerdida: PublicacionMascotaPerdida;
    isSaving: boolean;

    users: User[];

    distritos: Distrito[];

    mascotas: Mascota[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private publicacionMascotaPerdidaService: PublicacionMascotaPerdidaService,
        private userService: UserService,
        private distritoService: DistritoService,
        private mascotaService: MascotaService,
        private eventManager: JhiEventManager,
        private principal: Principal,
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.distritoService.query()
            .subscribe((res: ResponseWrapper) => { this.distritos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mascotaService.query()
            .subscribe((res: ResponseWrapper) => { this.mascotas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        if (this.publicacionMascotaPerdida.id === undefined) {
            this.principal.identity().then((account) => {
                this.publicacionMascotaPerdida.dueno = account;
                this.publicacionMascotaPerdida.estado = EstadoMascotaPerdida.PERDIDA;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.publicacionMascotaPerdida.id !== undefined) {
            this.subscribeToGuardarResponse(
                this.publicacionMascotaPerdidaService.update(this.publicacionMascotaPerdida));
        } else {
            this.subscribeToGuardarResponse(
                this.publicacionMascotaPerdidaService.create(this.publicacionMascotaPerdida));
        }
    }

    private subscribeToGuardarResponse(result: Observable<PublicacionMascotaPerdida>) {
        result.subscribe((res: PublicacionMascotaPerdida) =>
            this.onGuardarSuccess(res), (res: Response) => this.onGuardarError());
    }

    private onGuardarSuccess(result: PublicacionMascotaPerdida) {
        this.eventManager.broadcast({ name: 'publicacionMascotaPerdidaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onGuardarError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackDistritoById(index: number, item: Distrito) {
        return item.id;
    }

    trackMascotaById(index: number, item: Mascota) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-publicacion-mascota-perdida-popup',
    template: ''
})
export class PublicacionMascotaPerdidaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private publicacionMascotaPerdidaPopupService: PublicacionMascotaPerdidaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.publicacionMascotaPerdidaPopupService
                    .open(PublicacionMascotaPerdidaDialogComponent as Component, params['id']);
            } else {
                this.publicacionMascotaPerdidaPopupService
                    .open(PublicacionMascotaPerdidaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
