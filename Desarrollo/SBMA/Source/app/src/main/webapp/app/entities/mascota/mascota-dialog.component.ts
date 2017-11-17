import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Mascota } from './mascota.model';
import { MascotaPopupService } from './mascota-popup.service';
import { MascotaService } from './mascota.service';
import { Raza, RazaService } from '../raza';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';
import {Principal} from '../../shared/auth/principal.service';
import {Account} from '../../shared/user/account.model';

@Component({
    selector: 'jhi-mascota-dialog',
    templateUrl: './mascota-dialog.component.html'
})
export class MascotaDialogComponent implements OnInit {
    anhos: number;
    meses: number;
    mascota: Mascota;
    isSaving: boolean;

    razas: Raza[];

    users: User[];
    account: Account;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private mascotaService: MascotaService,
        private razaService: RazaService,
        private userService: UserService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager,
        private principal: Principal,
    ) {
    }

    ngOnInit() {
        if (this.mascota.id === undefined) {
            this.meses = 0;
            this.principal.identity().then((account) => {
                this.account = account;
                this.userService.find(this.account.login).subscribe((user) => {
                    this.mascota.dueno = user;
                    console.log(this.mascota);
                });
            });
        } else {
            this.meses = this.mascota.meses % 12;
            this.anhos = this.mascota.meses / 12;
        }
        this.isSaving = false;
        this.razaService.query()
            .subscribe((res: ResponseWrapper) => { this.razas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.mascota, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.mascota.meses = this.anhos * 12 + this.meses;
        if (this.mascota.id !== undefined) {
            this.subscribeToGuardarResponse(
                this.mascotaService.update(this.mascota));
        } else {
            this.subscribeToGuardarResponse(
                this.mascotaService.create(this.mascota));
        }
    }

    private subscribeToGuardarResponse(result: Observable<Mascota>) {
        result.subscribe((res: Mascota) =>
            this.onGuardarSuccess(res), (res: Response) => this.onGuardarError());
    }

    private onGuardarSuccess(result: Mascota) {
        this.eventManager.broadcast({ name: 'mascotaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onGuardarError() {
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
