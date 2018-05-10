package com.dubion.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dubion.domain.Album;
import com.dubion.domain.Artist;
import com.dubion.service.ArtistService;
import com.dubion.repository.ArtistRepository;
import com.dubion.service.NapsterAPI.NapsterDTOService;
import com.dubion.service.dto.NapsterAPI.NapsterArtist;
import com.dubion.service.dto.NapsterAPI.Search.Search;
import com.dubion.web.rest.errors.BadRequestAlertException;
import com.dubion.web.rest.util.HeaderUtil;
import com.dubion.service.dto.ArtistCriteria;
import com.dubion.service.ArtistQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Artist.
 */
@RestController
@RequestMapping("/api")
public class ArtistResource {

    private final Logger log = LoggerFactory.getLogger(ArtistResource.class);

    private static final String ENTITY_NAME = "artist";

    private final ArtistRepository artistRepository;

    private final ArtistService artistService;

    private final ArtistQueryService artistQueryService;

    private final NapsterDTOService napsterDTOService;

    public ArtistResource(ArtistRepository artistRepository, ArtistService artistService, ArtistQueryService artistQueryService, NapsterDTOService napsterDTOService){
        this.artistRepository = artistRepository;
        this.artistService = artistService;
        this.artistQueryService = artistQueryService;
        this.napsterDTOService = napsterDTOService;
    }

    /**
     * POST  /artists : Create a new artist.
     *
     * @param artist the artist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new artist, or with status 400 (Bad Request) if the artist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/artists")
    @Timed
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) throws URISyntaxException {
        log.debug("REST request to save Artist : {}", artist);
        if (artist.getId() != null) {
            throw new BadRequestAlertException("A new artist cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Artist result = artistService.save(artist);
        return ResponseEntity.created(new URI("/api/artist/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /artists : Updates an existing artist.
     *
     * @param artist the artist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated artist,
     * or with status 400 (Bad Request) if the artist is not valid,
     * or with status 500 (Internal Server Error) if the artist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/artists")
    @Timed
    public ResponseEntity<Artist> updateArtist(@RequestBody Artist artist) throws URISyntaxException {
        log.debug("REST request to update Artist : {}", artist);
        if (artist.getId() == null) {
            return createArtist(artist);
        }
        Artist result = artistService.save(artist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, artist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /artists : get all the artists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of artists in body
     */
    @GetMapping("/artists")
    @Timed
    public ResponseEntity<List<Artist>> getAllArtist(ArtistCriteria criteria) {
        log.debug("REST request to get Bands by criteria: {}", criteria);
        List<Artist> entityList = artistQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }
    /**
     * GET  /artists/:id : get the "id" artist.
     *
     * @param id the id of the artist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the artist, or with status 404 (Not Found)
     */
    @GetMapping("/artists/{id}")
    @Timed
    public ResponseEntity<Artist> getArtist(@PathVariable Long id) {
        log.debug("REST request to get Artist : {}", id);
        Artist artist = artistService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artist));
    }/**
     * GET  /songs/:id : get the "id" song.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the song, or with status 404 (Not Found)
     */
    @GetMapping("/artists/top")
    @Timed
    public ResponseEntity<NapsterArtist> getTopArtist() {
        NapsterArtist artist = napsterDTOService.getTopArtistNap();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(artist));
    }
    /**
     * GET  /songs/:id : get the "id" song.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the song, or with status 404 (Not Found)
     */
    @GetMapping("/artists/top2")
    @Timed
    public ResponseEntity<List<Artist>> importTopArtist() throws IOException {
        List<Artist> song = napsterDTOService.importTopArtist();
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(song));
    }
    /**
     * GET  /songs/:id : get the "id" song.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the song, or with status 404 (Not Found)
     */
    @GetMapping("/artist/search/{artistName}")
    public List<com.dubion.service.dto.NapsterAPI.Search.Artists> getBandSearch(@PathVariable String artistName){
        Search band = napsterDTOService.searchBands(artistName);
        return band.getSearch().getData().getArtists();
    }
    /**
     * DELETE  /artists/:id : delete the "id" artist.
     *
     * @param id the id of the artist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/artists/{id}")
    @Timed
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        log.debug("REST request to delete Artist : {}", id);
        artistService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
