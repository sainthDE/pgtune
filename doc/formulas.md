# Formulas

## Input Parameters

- `dbVersion`
- `osType`
- `dbApplication`
- `ram`
- `cpus`
- `connections`
- `dataStorage`

## Output Parameters

### `max_connections`

If `connections` was specified then adopt `connections`

otherwise the value depends on `dbApplication`:

- `WEB` => 200
- `OLTP` => 300
- `DATA_WAREHOUSE` => 40
- `DESKTOP` => 20
- `MIXED` => 100

### `shared_buffers`

If `dbApplication` == `Desktop` then `ram` / 16

otherwise `ram` / 4

If `osType` == `Windows` use 512MB as maximum

## `effective_cache_size`

If `dbApplication` == `Desktop` then `ram` / 4

otherwise `ram` * 3 / 4

## `maintenance_work_mem`

If `dbApplication` == `DATA_WAREHOUSE` then `ram` / 8

otherwise `ram` / 16

Use 2GB as maximum, but if `osType` == `Windows` use 2GB - 1MB as maximum

### checkpointSegments

#### `dbVersion` < 9.5

- `checkpoint_segments` = Depends on `dbApplication`:
  - `WEB` => 32
  - `OLTP` => 64
  - `DATA_WAREHOUSE` => 128
  - `DESKTOP` => 3
  - `MIXED` => 32

#### `dbVersion` >= 9.5

- `min_wal_size` = Depends on `dbApplication`:
  - `WEB` => 1GB
  - `OLTP` => 2GB
  - `DATA_WAREHOUSE` => 4GB
  - `DESKTOP` => 100MB
  - `MIXED` => 1GB
- `max_wal_size` = Depends on `dbApplication`:
  - `WEB` => 2GB
  - `OLTP` => 4GB
  - `DATA_WAREHOUSE` => 8GB
  - `DESKTOP` => 1GB
  - `MIXED` => 2GB

### `checkpoint_completion_target`

Depends on `dbApplication`:

- `WEB` => 0.7
- `OLTP` => 0.9
- `DATA_WAREHOUSE` => 0.9
- `DESKTOP` => 0.5
- `MIXED` => 0.9

### `wal_buffers`

3% of `shared_buffers`, if value >= 14MB then round up to 16MB

- Maximum: 16MB
- Minimum: 32KB

### `default_statistics_target`

If `dbApplication` == `DATA_WAREHOUSE` then 500

otherwise 100

### `random_page_cost`

If `dataStorage` == `HDD` then 4

otherwise 1.1

### `effective_io_concurrency`

Depends on `dataStorage`:

- `HDD` => 2
- `SSD` => 200
- `SAN` => 300

### parallelSettings

#### `dbVersion` >= 9.5

- `max_worker_processes` = `cpus`

#### `dbVersion` >= 9.6

- `max_parallel_workers_per_gather` = `cpus` / 2

#### `dbVersion` >= 10

- `max_parallel_workers` = `cpus`

### `work_mem`

`workMemValue` = (`ram` - `shared_buffers`) / (3 * `max_connections`) / `max_parallel_workers_per_gather`

Depends on `dbApplication`:

- `WEB` => `workMemValue`
- `OLTP` => `workMemValue`
- `DATA_WAREHOUSE` => `workMemValue` / 2
- `DESKTOP` => `workMemValue` / 6
- `MIXED` => `workMemValue` / 2

- Minimum 64 KB
