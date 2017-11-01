import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PublicacionMascotaPerdida } from './publicacion-mascota-perdida.model';
import { PublicacionMascotaPerdidaService } from './publicacion-mascota-perdida.service';

@Injectable()
export class PublicacionMascotaPerdidaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private publicacionMascotaPerdidaService: PublicacionMascotaPerdidaService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.publicacionMascotaPerdidaService.find(id).subscribe((publicacionMascotaPerdida) => {
                    publicacionMascotaPerdida.fecha = this.datePipe
                        .transform(publicacionMascotaPerdida.fecha, 'yyyy-MM-ddTHH:mm:ss');
                    publicacionMascotaPerdida.fechaEncuentro = this.datePipe
                        .transform(publicacionMascotaPerdida.fechaEncuentro, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.publicacionMascotaPerdidaModalRef(component, publicacionMascotaPerdida);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.publicacionMascotaPerdidaModalRef(component, new PublicacionMascotaPerdida());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    publicacionMascotaPerdidaModalRef(component: Component, publicacionMascotaPerdida: PublicacionMascotaPerdida): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.publicacionMascotaPerdida = publicacionMascotaPerdida;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
