import { CrawlDataType, CrawlerConfigStatus, IsArray, IsLink, SelectorType } from "./crawlerConfigEnum"

export type CreateCrawlerConfigType = {
    name: string,
    baseUrl: string,
    status: CrawlerConfigStatus,
    selectorList: string,
    selectors: Array<Selector>,
    selectorDetails: Array<Selector>
}

export type Selector = {
    key: string,
    selector: string,
    type: SelectorType,
    attribute: string,
    isLink: IsLink,
    isArray: IsArray,
    dataType: CrawlDataType
}

export type ST = keyof typeof SelectorType;



