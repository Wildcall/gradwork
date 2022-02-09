Usage:
====
### Base url:
```
http://localhost:8000/api/v1
```

## REST API
### Site controller:
<details>
 <summary>Get list of sites</summary>

#### **Request**:
```
GET /site

{ base url }/site
 ```
#### **Response**:
```
[
    {
        "id": 1,
        "status": "INDEXED",
        "path": "https://mysite.com/",
        "name": "My first site"
    },
    {
        "id": 2,
        "status": "SAVED",
        "path": "https://mysecondsite.com/",
        "name": "My second site"
    }
]
 ```
#### **Exceptions**:
```

```
</details>

<details>
 <summary>Get a specific site</summary>

#### **Request**:
```
GET /site/{id}

{ base url }/site/{id}
 ```
#### **Response**:
```
{
    "id": 1,
    "status": "INDEXED",
    "statusTime": "2022-01-28 18:19:01",
    "path": "https://mysite.com/",
    "name": "MySite",
    "crawlerId": 1,
    "indexerId": 1
}
 ```
#### **Exceptions**:
```
Site with id: {id} not found
```
</details>

<details>
 <summary>Get a statistics for specific site </summary>

#### **Request**:
```
GET /site/{id}/stat

{ base url }/site/{id}/stat
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details>
 <summary>Get all pages for specific site </summary>

#### **Request**:
```
GET /site/{id}/page

{ base url }/site/{id}/page
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details>
 <summary>Get all errors for specific site </summary>

#### **Request**:
```
GET /site/{id}/error

{ base url }/site/{id}/error
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details>
 <summary>Delete all errors for specific site </summary>

#### **Request**:
```
DELETE /site/{id}/error

{ base url }/site/{id}/error
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details>
 <summary>Create a new site</summary>

#### **Request**:
```
POST /site

{ base url }/site

{
    "path":"https://mysite.com/",
    "name":"MySite"
}
 ```
#### **Response**:
```
{
    "id": 1,
    "status": "INORDER",
    "statusTime": "2022-01-30 14:08:52",
    "path": "https://mysite.com/",
    "name": "MySite",
    "crawlerId": 1,
    "indexerId": 1
}
 ```
#### **Exceptions**:
```
Site with path: https://mysite.com/ already has existed

Site with name: MySite already has existed

Validation failed for object='siteDto'. Error count: 
```
</details>

<details>
 <summary>Change a site</summary>

#### **Request**:
```
PUT /site/{id}

{ base url }/site/{id}

{
    "name": "NotMySite",
    "crawlerId": 2,
    "indexerId": 2
}
 ```
#### **Response**:
```
{
    "id": 1,
    "status": "INORDER",
    "statusTime": "2022-01-30 14:08:52",
    "path": "https://mysite.com/",
    "name": "NotMySite",
    "crawlerId": 2,
    "indexerId": 2
}
 ```
#### **Exceptions**:
```
Site with id: {id} not found

Site with name: NotMySite already has existed

Crawler preset with id: {crawlerId} not found

Indexer preset with id: {indexerId} not found

Validation failed for object='siteDto'. Error count: 
```
</details>

<details>
 <summary>Delete a site</summary>

#### **Request**:
```
DELETE /site/{id}

{ base url }/site/{id}
 ```
#### **Response**:
```
{id}
 ```
#### **Exceptions**:
```
Site with id: {id} not found
```
</details>

### Crawler controller:
<details><summary>Descriptions</summary>
Crawler - this is algorithm which walking on the site tree and save content on local database.

We can set up a few parameters:
- `preset`
    - boolean flag that indicates if the setting is a preset
- `presetName`
    - name of preset
- `description`
    - description of preset
- `parallelism`
    - level of parallelism when crawler's working. `parallelism >= 1`
- `timeout`
    - time in microseconds before crawler can skip page if page not available `timeout >= 0`
- `delay`
    - time in microseconds between fetch two pages `delay >= 0`
- `reconnect`
    - amount of try fetch page before crawler can skip page if page not available `reconnect >= 1`
- `userAgent`
    - just User Agent
- `referrer`
    - referrer address

Default parameters:
- `preset = true`
- `presetName = default`
- `description = default`
- `parallelism = 4`
- `timeout = 5000`
- `delay = 100`
- `reconnect = Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36`
- `userAgent = https://www.google.com`
- `referrer = 1`

Default parameters are automatically set when adding all new sites.
</details>

<details><summary>Starting crawler algorithm</summary>

This request starting crawler algorithm which will be walked on site tree, parse and save content on local database.

#### **Request**:
```
GET /crawler/start/{id}

{ base url }/crawler/start/{id}
 ``` 
#### **Response**:
```
Start saving for id: {id}
 ```
#### **Exceptions**:
```
Site with id: {id} not found

Saving or indexing for site https://mysite.ru/ has already started
```
</details>

<details><summary>Get list of crawlers settings</summary>

#### **Request**:
```
GET /crawler

{ base url }/crawler
 ```
#### **Response**:
```
[
    {
        "id": 1,
        "preset": true,
        "presetName": "default"
    },
    {
        "id": 2,
        "preset": false,
        "presetName": "test"
    }
]
 ```
#### **Exceptions**:
```
```
</details>

<details>
 <summary>Get a specific crawler settings</summary>

#### **Request**:
```
GET /crawler/{id}

{ base url }/crawler/{id}
 ```
#### **Response**:
```
{
    "id": 1,
    "preset": true,
    "presetName": "default",
    "description": "default",
    "parallelism": 4,
    "timeout": 5000,
    "delay": 100,
    "reconnect": 1,
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
    "referrer": "https://www.google.com"
}
 ```
#### **Exceptions**:
```
Crawler preset with id: {id} not found
```
</details>

<details>
 <summary>Create a new crawler settings</summary>

#### **Request**:
```
POST /crawler

{ base url }/crawler

{
    "preset": false,
    "presetName": "test",
    "description": "test",
    "parallelism": 8,
    "timeout": 2000,
    "delay": 1000,
    "reconnect": 5,
    "userAgent": "Google Chrome",
    "referrer": "https://www.yandex.ru"
}
 ```
#### **Response**:
```
{
    "id": 2,
    "preset": false,
    "presetName": "test",
    "description": "test",
    "parallelism": 8,
    "timeout": 2000,
    "delay": 1000,
    "reconnect": 5,
    "userAgent": "Google Chrome",
    "referrer": "https://www.yandex.ru"
}
 ```
#### **Exceptions**:
```
Crawler preset with name: test already has existed

Validation failed for object='crawlerSettingsDto'. Error count: 
```
</details>

<details>
 <summary>Change a crawler settings</summary>

#### **Request**:
```
PUT /crawler/{id}

{ base url }/crawler/{id}

{
    "preset": true,
    "presetName": "newName",
    "description": "new description",
    "parallelism": 4,
    "timeout": 5000,
    "delay": 0,
    "reconnect": 1,
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
    "referrer": "https://www.google.com"
}
 ```
#### **Response**:
```
{
    "id": 1,
    "preset": true,
    "presetName": "newName",
    "description": "new description",
    "parallelism": 4,
    "timeout": 5000,
    "delay": 0,
    "reconnect": 1,
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
    "referrer": "https://www.google.com"
}
 ```
#### **Exceptions**:
```
Crawler preset with name: newName already has existed

Crawler preset with id: {id} not found

Validation failed for object='crawlerSettingsDto'. Error count: 
```
</details>

<details>
 <summary>Delete a crawler settings</summary>

#### **Request**:
```
DELETE /crawler/{id}

{ base url }/crawler/{id}
 ```
#### **Response**:
```
{id}
 ```
#### **Exceptions**:
```
Crawler preset with id: {id} not found

Crawler settings with id {id} cannot be removed. Reason: there are sites with current settings
```
</details>

### Indexer controller:
<details><summary>Descriptions</summary>
Indexer - this is algorithm which parsing page from local database and create lemmas and index.

We can set up a few parameters:
- `preset`
    - boolean flag that indicates if the setting is a preset
- `presetName`
    - name of preset
- `description`
    - description of preset
- `selectors`
    - list of `html`-tags, that will be used during the algorithm, split symbol `,`
- `weights`
    - list of weight coefficient, that will be used during the calculation of the relevance of the lemma, split symbol `,`

Default parameters:
- `preset = true`
- `presetName = default`
- `description = default`
- `selectors = title,body`
- `weights = 1,0.8`

Default parameters are automatically set when adding all new sites.
</details>

<details><summary>Starting indexer algorithm</summary>

This request starting indexer algorithm which will be parsed page from local database, create lemmas and index.

#### **Request**:
```
GET /indexer/start/{id}

{ base url }/indexer/start/{id}
 ``` 
#### **Response**:
```
Start indexing for id: {id}
 ```
#### **Exceptions**:
```
Site with id: {id} not found

Saving or indexing for site https://mysite.ru/ has already started
```
</details>

<details><summary>Get list of indexers settings</summary>

#### **Request**:
```
GET /indexer

{ base url }/indexer
 ```
#### **Response**:
```
[
    {
        "id": 1,
        "preset": true,
        "presetName": "default"
    },
    {
        "id": 2,
        "preset": false,
        "presetName": "test"
    }
]
 ```
#### **Exceptions**:
```
```
</details>

<details>
 <summary>Get a specific indexer settings</summary>

#### **Request**:
```
GET /indexer/{id}

{ base url }/indexer/{id}
 ```
#### **Response**:
```
{
    "id": 1,
    "preset": true,
    "presetName": "default",
    "description": "default",
    "selectorWeight": {
        "body": 0.8,
        "title": 1.0
    }
}
 ```
#### **Exceptions**:
```
Indexer preset with id: {id} not found
```
</details>

<details>
 <summary>Create a new indexer settings</summary>

#### **Request**:
```
POST /indexer

{ base url }/indexer

{
    "preset": true,
    "presetName": "test",
    "description": "test",
    "selectorWeight": {
        "body": 0.5,
        "title": 1.0,
        "head": 2.0
    }
}
 ```
#### **Response**:
```
{
    "id": 2,
    "preset": true,
    "presetName": "test",
    "description": "test",
    "selectorWeight": {
        "body": 0.5,
        "title": 1.0,
        "head": 2.0
    }
}
 ```
#### **Exceptions**:
```
Indexer preset with name: test already has existed

Validation failed for object='indexerSettingsDto'. Error count:
```
</details>

<details>
 <summary>Change a indexer settings</summary>

#### **Request**:
```
PUT /indexer/{id}

{ base url }/indexer/{id}

{
    "preset": true,
    "presetName": "newName",
    "description": "New description",
    "selectorWeight": {
        "body": 0.5,
        "title": 1.0,
        "head": 2.0
    }
}
 ```
#### **Response**:
```
{
    "id": 1,
    "preset": true,
    "presetName": "newName",
    "description": "New description",
    "selectorWeight": {
        "head": 2.0,
        "body": 0.5,
        "title": 1.0
    }
}
 ```
#### **Exceptions**:
```
Indexer preset with id: {id} not found

Indexer preset with name: newName already has existed

Validation failed for object='indexerSettingsDto'. Error count:
```
</details>

<details>
 <summary>Delete a indexer settings</summary>

#### **Request**:
```
DELETE /indexer/{id}

{ base url }/indexer/{id}
 ```
#### **Response**:
```
{id}
 ```
#### **Exceptions**:
```
Indexer preset with id: {id} not found

Indexer settings with id {id} cannot be removed. Reason: there are sites with current settings
```
</details>

### Statistics controller:
<details><summary>Descriptions</summary>
Can return a few types of statistics
</details>

<details><summary>Get total statistics</summary>

#### **Request**:
```
GET /stat

{ base url }/stat
 ``` 
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details><summary>Get statistics for all crawlers</summary>

#### **Request**:
```
GET /stat/crawler

{ base url }/stat/crawler
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details><summary>Get statistic for specific crawler</summary>

#### **Request**:
```
GET /stat/crawler/{id}

{ base url }/stat/crawler/{id}
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details><summary>Get statistics for all indexers</summary>

#### **Request**:
```
GET /stat/indexer

{ base url }/stat/indexer
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details><summary>Get statistic for specific indexer</summary>

#### **Request**:
```
GET /stat/indexer/{id}

{ base url }/stat/indexer/{id}
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

### Page controller:
<details><summary>Descriptions</summary>
Descriptions
</details>

<details><summary>Get all pages</summary>

#### **Request**:
```
GET /page

{ base url }/page
 ``` 
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details><summary>Get specific page</summary>

#### **Request**:
```
GET /page/{id}

{ base url }/page/{id}
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

<details><summary>Change list of pages</summary>

#### **Request**:
```
PUT /page

{ base url }/page
 ```
#### **Response**:
```
Not implemented yet
 ```
#### **Exceptions**:
```
Not implemented yet
```
</details>

### Error controller:
<details><summary>Descriptions</summary>
Get some information about errors
</details>

<details><summary>Get all errors for site</summary>

#### **Request**:
```
GET /error

{ base url }/error
 ``` 
#### **Response**:
```
[
    {
        "id": 1,
        "text": "Unhandled content type. Must be text/*, application/xml, or application/*+xml",
        "errorTime": "2022-02-09 19:52:32",
        "pagePath": "https://mysite.ru/blog/"
    }
]
 ```
#### **Exceptions**:
```
```
</details>