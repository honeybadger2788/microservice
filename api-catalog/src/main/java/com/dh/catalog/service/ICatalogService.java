package com.dh.catalog.service;

import com.dh.catalog.service.impl.CatalogService;

public interface ICatalogService {
    CatalogService.Catalog getCatalog(String genre) throws Exception;
}
