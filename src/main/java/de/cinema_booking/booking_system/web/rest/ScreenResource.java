package de.cinema_booking.booking_system.web.rest;

import de.cinema_booking.booking_system.domain.Screen;
import de.cinema_booking.booking_system.repository.ScreenRepository;
import de.cinema_booking.booking_system.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link de.cinema_booking.booking_system.domain.Screen}.
 */
@RestController
@RequestMapping("/api/screens")
@Transactional
public class ScreenResource {

    private final Logger log = LoggerFactory.getLogger(ScreenResource.class);

    private static final String ENTITY_NAME = "screen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScreenRepository screenRepository;

    public ScreenResource(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    /**
     * {@code POST  /screens} : Create a new screen.
     *
     * @param screen the screen to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new screen, or with status {@code 400 (Bad Request)} if the screen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Screen> createScreen(@RequestBody Screen screen) throws URISyntaxException {
        log.debug("REST request to save Screen : {}", screen);
        if (screen.getId() != null) {
            throw new BadRequestAlertException("A new screen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        screen = screenRepository.save(screen);
        return ResponseEntity.created(new URI("/api/screens/" + screen.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, screen.getId().toString()))
            .body(screen);
    }

    /**
     * {@code PUT  /screens/:id} : Updates an existing screen.
     *
     * @param id the id of the screen to save.
     * @param screen the screen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated screen,
     * or with status {@code 400 (Bad Request)} if the screen is not valid,
     * or with status {@code 500 (Internal Server Error)} if the screen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Screen> updateScreen(@PathVariable(value = "id", required = false) final Long id, @RequestBody Screen screen)
        throws URISyntaxException {
        log.debug("REST request to update Screen : {}, {}", id, screen);
        if (screen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, screen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!screenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        screen = screenRepository.save(screen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, screen.getId().toString()))
            .body(screen);
    }

    /**
     * {@code PATCH  /screens/:id} : Partial updates given fields of an existing screen, field will ignore if it is null
     *
     * @param id the id of the screen to save.
     * @param screen the screen to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated screen,
     * or with status {@code 400 (Bad Request)} if the screen is not valid,
     * or with status {@code 404 (Not Found)} if the screen is not found,
     * or with status {@code 500 (Internal Server Error)} if the screen couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Screen> partialUpdateScreen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Screen screen
    ) throws URISyntaxException {
        log.debug("REST request to partial update Screen partially : {}, {}", id, screen);
        if (screen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, screen.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!screenRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Screen> result = screenRepository
            .findById(screen.getId())
            .map(existingScreen -> {
                if (screen.getScreenID() != null) {
                    existingScreen.setScreenID(screen.getScreenID());
                }
                if (screen.getName() != null) {
                    existingScreen.setName(screen.getName());
                }
                if (screen.getTotalSeats() != null) {
                    existingScreen.setTotalSeats(screen.getTotalSeats());
                }

                return existingScreen;
            })
            .map(screenRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, screen.getId().toString())
        );
    }

    /**
     * {@code GET  /screens} : get all the screens.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of screens in body.
     */
    @GetMapping("")
    public List<Screen> getAllScreens() {
        log.debug("REST request to get all Screens");
        return screenRepository.findAll();
    }

    /**
     * {@code GET  /screens/:id} : get the "id" screen.
     *
     * @param id the id of the screen to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the screen, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreen(@PathVariable("id") Long id) {
        log.debug("REST request to get Screen : {}", id);
        Optional<Screen> screen = screenRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(screen);
    }

    /**
     * {@code DELETE  /screens/:id} : delete the "id" screen.
     *
     * @param id the id of the screen to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable("id") Long id) {
        log.debug("REST request to delete Screen : {}", id);
        screenRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
