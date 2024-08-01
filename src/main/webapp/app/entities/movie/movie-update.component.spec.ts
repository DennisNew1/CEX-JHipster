/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import MovieUpdate from './movie-update.vue';
import MovieService from './movie.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

type MovieUpdateComponentType = InstanceType<typeof MovieUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const movieSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<MovieUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Movie Management Update Component', () => {
    let comp: MovieUpdateComponentType;
    let movieServiceStub: SinonStubbedInstance<MovieService>;

    beforeEach(() => {
      route = {};
      movieServiceStub = sinon.createStubInstance<MovieService>(MovieService);
      movieServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          movieService: () => movieServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(MovieUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(MovieUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.movie = movieSample;
        movieServiceStub.update.resolves(movieSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(movieServiceStub.update.calledWith(movieSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        movieServiceStub.create.resolves(entity);
        const wrapper = shallowMount(MovieUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.movie = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(movieServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        movieServiceStub.find.resolves(movieSample);
        movieServiceStub.retrieve.resolves([movieSample]);

        // WHEN
        route = {
          params: {
            movieId: '' + movieSample.id,
          },
        };
        const wrapper = shallowMount(MovieUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.movie).toMatchObject(movieSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        movieServiceStub.find.resolves(movieSample);
        const wrapper = shallowMount(MovieUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
