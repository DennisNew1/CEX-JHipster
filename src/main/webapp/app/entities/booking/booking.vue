<template>
  <div>
    <h2 id="page-heading" data-cy="BookingHeading">
      <span v-text="t$('jhipsterApp.booking.home.title')" id="booking-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('jhipsterApp.booking.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'BookingCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-booking"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('jhipsterApp.booking.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && bookings && bookings.length === 0">
      <span v-text="t$('jhipsterApp.booking.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="bookings && bookings.length > 0">
      <table class="table table-striped" aria-describedby="bookings">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.booking.bookingID')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.booking.movieTime')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.booking.seatID')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.booking.paymentStatus')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.booking.movie')"></span></th>
            <th scope="row"><span v-text="t$('jhipsterApp.booking.customer')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="booking in bookings" :key="booking.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'BookingView', params: { bookingId: booking.id } }">{{ booking.id }}</router-link>
            </td>
            <td>{{ booking.bookingID }}</td>
            <td>{{ formatDateShort(booking.movieTime) || '' }}</td>
            <td>{{ booking.seatID }}</td>
            <td>{{ booking.paymentStatus }}</td>
            <td>
              <div v-if="booking.movie">
                <router-link :to="{ name: 'MovieView', params: { movieId: booking.movie.id } }">{{ booking.movie.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="booking.customer">
                <router-link :to="{ name: 'CustomerView', params: { customerId: booking.customer.id } }">{{
                  booking.customer.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'BookingView', params: { bookingId: booking.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'BookingEdit', params: { bookingId: booking.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(booking)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="jhipsterApp.booking.delete.question" data-cy="bookingDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-booking-heading" v-text="t$('jhipsterApp.booking.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-booking"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeBooking()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./booking.component.ts"></script>
