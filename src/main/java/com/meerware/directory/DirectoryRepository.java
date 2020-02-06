package com.meerware.directory;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

/**
 *
 */
interface DirectoryRepository extends CrudRepository<Directory, UUID> {


}
