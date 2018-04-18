package com.dubion.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dubion.domain.Album;
import com.dubion.domain.FavouriteAlbum;
import com.dubion.repository.FavouriteAlbumRepository;
import com.dubion.repository.UserRepository;
import com.dubion.security.SecurityUtils;
import com.dubion.service.AlbumService;
import com.dubion.service.FavouriteAlbumQueryService;
import com.dubion.service.FavouriteAlbumService;
import com.dubion.service.dto.FavouriteAlbumCriteria;
import com.dubion.web.rest.errors.BadRequestAlertException;
import com.dubion.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FavouriteAlbum.
 */
@RestController
@RequestMapping("/api")
public class FavouriteAlbumResource {

    private final Logger log = LoggerFactory.getLogger(FavouriteAlbumResource.class);

    private static final String ENTITY_NAME = "favouriteAlbum";

    private final FavouriteAlbumRepository favouriteAlbumRepository;

    private final FavouriteAlbumService favouriteAlbumService;

    private final AlbumService albumService;

    private final FavouriteAlbumQueryService favouriteAlbumQueryService;

    private final UserRepository userRepository;

    public FavouriteAlbumResource(FavouriteAlbumRepository favouriteAlbumRepository, FavouriteAlbumService favouriteAlbumService, AlbumService albumService, FavouriteAlbumQueryService favouriteAlbumQueryService, UserRepository userRepository) {
        this.favouriteAlbumRepository = favouriteAlbumRepository;
        this.favouriteAlbumService = favouriteAlbumService;
        this.albumService = albumService;
        this.favouriteAlbumQueryService = favouriteAlbumQueryService;
        this.userRepository = userRepository;
    }

    /**
     * POST  /favourite-albums : Create a new favouriteAlbum.
     *
     * @param favouriteAlbum the favouriteAlbum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new favouriteAlbum, or with status 400 (Bad Request) if the favouriteAlbum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/favourite-albums")
    @Timed
    public ResponseEntity<FavouriteAlbum> createFavouriteAlbum(@RequestBody FavouriteAlbum favouriteAlbum) throws URISyntaxException {
        log.debug("REST request to save FavouriteAlbum : {}", favouriteAlbum);
        if (favouriteAlbum.getId() != null) {
            throw new BadRequestAlertException("A new favouriteAlbum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        favouriteAlbum.setDate(LocalDate.now());
        favouriteAlbum.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());

        Optional<FavouriteAlbum> favouriteAlbumOptional = favouriteAlbumRepository.findByAlbumAndUserLogin(favouriteAlbum.getAlbum(), SecurityUtils.getCurrentUserLogin());

        FavouriteAlbum result;

        if (favouriteAlbumOptional.isPresent()) {
            FavouriteAlbum favouriteAlbumActual = favouriteAlbumOptional.get();
            favouriteAlbumActual.setLiked(favouriteAlbum.getLiked());
            favouriteAlbum.setDate(LocalDate.now());
            result = favouriteAlbumService.save(favouriteAlbumActual);
        }else{
            favouriteAlbum.setDate(LocalDate.now());
            favouriteAlbum.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get());
            result = favouriteAlbumService.save(favouriteAlbum);
        }

        return ResponseEntity.created(new URI("/api/favourite-albums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /favourite-albums : Updates an existing favouriteAlbum.
     *
     * @param favouriteAlbum the favouriteAlbum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated favouriteAlbum,
     * or with status 400 (Bad Request) if the favouriteAlbum is not valid,
     * or with status 500 (Internal Server Error) if the favouriteAlbum couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/favourite-albums")
    @Timed
    public ResponseEntity<FavouriteAlbum> updateFavouriteAlbum(@RequestBody FavouriteAlbum favouriteAlbum) throws URISyntaxException {
        log.debug("REST request to update FavouriteAlbum : {}", favouriteAlbum);
        if (favouriteAlbum.getId() == null) {
            return createFavouriteAlbum(favouriteAlbum);
        }
        FavouriteAlbum result = favouriteAlbumService.save(favouriteAlbum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, favouriteAlbum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /favourite-albums : get all the favouriteAlbums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of favouriteAlbums in body
     */
    @GetMapping("/favourite-albums")
    @Timed
    public ResponseEntity<List<FavouriteAlbum>> getAllFavouriteAlbums(FavouriteAlbumCriteria criteria) {
        log.debug("REST request to get all FavouriteAlbums by criteria: {}", criteria);
        List<FavouriteAlbum> entityList = favouriteAlbumQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
        }

    /**
     * GET  /favourite-albums/:id : get the "id" favouriteAlbum.
     *
     * @param id the id of the favouriteAlbum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the favouriteAlbum, or with status 404 (Not Found)
     */
    @GetMapping("/favourite-albums/{id}")
    @Timed
    public ResponseEntity<FavouriteAlbum> getFavouriteAlbum(@PathVariable Long id) {
        log.debug("REST request to get FavouriteAlbum : {}", id);
        FavouriteAlbum favouriteAlbum = favouriteAlbumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(favouriteAlbum));
    }


    @GetMapping("/favourite-albums/album/{id}")
    @Timed
    public ResponseEntity<FavouriteAlbum> getFavouriteByAlbum(@PathVariable Long id) {
        log.debug("REST request to get FavouriteAlbum : {}", id);
        Album album = albumService.findOne(id);

        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(
                favouriteAlbumRepository.findByAlbumAndUserLogin(album,SecurityUtils.getCurrentUserLogin()).get()));
    }
    /**
     * DELETE  /favourite-albums/:id : delete the "id" favouriteAlbum.
     *
     * @param id the id of the favouriteAlbum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/favourite-albums/{id}")
    @Timed
    public ResponseEntity<Void> deleteFavouriteAlbum(@PathVariable Long id) {
        log.debug("REST request to delete FavouriteAlbum : {}", id);
        favouriteAlbumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
