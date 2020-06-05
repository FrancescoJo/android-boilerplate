package com.github.fj.android.awesomeapp.core

/**
 * A type placeholder for 'Data source'.
 *
 * Data source is responsible for preparing data from various locations(memory, persistent storage,
 * network, etc.)
 *
 * A collection of data sources could be made when multiple-level caching is required.
 * In that case there could be a 'DataSourceMuxer' that multiplexes various data from sources.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
internal interface DataSource
