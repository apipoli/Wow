/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { AppTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EdadDetailComponent } from '../../../../../../main/webapp/app/entities/edad/edad-detail.component';
import { EdadService } from '../../../../../../main/webapp/app/entities/edad/edad.service';
import { Edad } from '../../../../../../main/webapp/app/entities/edad/edad.model';

describe('Component Tests', () => {

    describe('Edad Management Detail Component', () => {
        let comp: EdadDetailComponent;
        let fixture: ComponentFixture<EdadDetailComponent>;
        let service: EdadService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AppTestModule],
                declarations: [EdadDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EdadService,
                    JhiEventManager
                ]
            }).overrideTemplate(EdadDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EdadDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EdadService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Edad(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.edad).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
