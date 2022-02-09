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
        "name": "MySite"
    }
]
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
    "crawlerId": 1
}
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
    "crawlerId": 1
}
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
    "crawlerId": 2
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
    "crawlerId": 2
}
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

</details>

### Crawler controller:

<details>
 <summary>Get list of crawlers settings</summary>

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
    }
]
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
    "timeout": 2000,
    "delay": 0,
    "reconnect": 1,
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
    "referrer": "https://www.google.com"
}
 ```

</details>

<details>
 <summary>Create a new crawler settings</summary>

#### **Request**:
```
POST /crawler

{ base url }/crawler

{
    "preset": true,
    "presetName": "default",
    "description": "default",
    "parallelism": 4,
    "timeout": 2000,
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
    "presetName": "default",
    "description": "default",
    "parallelism": 4,
    "timeout": 2000,
    "delay": 0,
    "reconnect": 1,
    "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36",
    "referrer": "https://www.google.com"
}
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

</details>

<details>
 <summary>Start saving a site</summary>

#### **Request**:
```
GET /crawler/start/{id}

{ base url }/crawler/start/{id}
 ```

#### **Response**:
```
{id}
 ```

</details>

<details>
 <summary>Stop saving a site</summary>

#### **Request**:
```
GET /crawler/stop/{id}

{ base url }/crawler/stop/{id}
 ```

#### **Response**:
```
{id}
 ```

</details>