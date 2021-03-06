package com.dubion.service;

import com.dubion.domain.Song;
import com.dubion.repository.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Song.
 */
@Service
@Transactional
public class SongService {

    private final Logger log = LoggerFactory.getLogger(SongService.class);

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    /**
     * Save a song.
     *
     * @param song the entity to save
     * @return the persisted entity
     */
    public Song save(Song song) {
        log.debug("Request to save Song : {}", song);
        return songRepository.save(song);
    }

    /**
     *  Get all the songs.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Song> findAll() {
        log.debug("Request to get all Songs");
        return songRepository.findAllWithEagerRelationships();
    }

    /**
     *  Get one song by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Song findOne(Long id) {
        log.debug("Request to get Song : {}", id);
        return songRepository.findOneWithEagerRelationships(id);
    }

    /**
     *  Delete the  song by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Song : {}", id);
        songRepository.delete(id);
    }
}
