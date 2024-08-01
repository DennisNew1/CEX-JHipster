/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Movie from './movie.vue';
import MovieService from './movie.service';
import AlertService from '@/shared/alert/alert.service';

type MovieComponentType = InstanceType<typeof Movie>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Movie Management Component', () => {
    let movieServiceStub: SinonStubbedInstance<MovieService>;
    let mountOptions: MountingOptions<MovieComponentType>['global'];

    beforeEach(() => {
      movieServiceStub = sinon.createStubInstance<MovieService>(MovieService);
      movieServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          movieService: () => movieServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        movieServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Movie, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(movieServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.movies[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: MovieComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Movie, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        movieServiceStub.retrieve.reset();
        movieServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        movieServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeMovie();
        await comp.$nextTick(); // clear components

        // THEN
        expect(movieServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(movieServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
