import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JadeTestModule } from '../../../test.module';
import { ModeleCommandeDetailComponent } from 'app/entities/modele-commande/modele-commande-detail.component';
import { ModeleCommande } from 'app/shared/model/modele-commande.model';

describe('Component Tests', () => {
  describe('ModeleCommande Management Detail Component', () => {
    let comp: ModeleCommandeDetailComponent;
    let fixture: ComponentFixture<ModeleCommandeDetailComponent>;
    const route = ({ data: of({ modeleCommande: new ModeleCommande(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JadeTestModule],
        declarations: [ModeleCommandeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ModeleCommandeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModeleCommandeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load modeleCommande on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modeleCommande).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
