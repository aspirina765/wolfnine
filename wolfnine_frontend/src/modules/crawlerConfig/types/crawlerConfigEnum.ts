export enum CrawlerConfigStatus {
  ACTIVE = 1,
  DEACTIVE = 0,
  DELETED = -1,
}

export enum SelectorType {
  GET_TEXT = 1,
  GET_ATTRIBUTE = 2,
  GET_HTML_CONTENT = 3,
}

export enum IsLink {
  ACTIVE = 1,
  DEACTIVE = 0,
}

export enum IsArray {
  ACTIVE = 1,
  DEACTIVE = 0,
}

export enum CrawlDataType {
  TEXT = 1,
  IMAGE_LINK = 2,
  NORMAL_LINK = 3,
}
