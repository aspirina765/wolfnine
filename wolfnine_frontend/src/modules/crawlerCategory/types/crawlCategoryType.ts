import { CrawlerConfigStatus } from "../../crawlerConfig/types/crawlerConfigEnum"

export declare type CreateCrawlerCategoryType = {
    name: string,
    link: string,
    crawlConfigId: number,
    categoryId: number,
    status: CrawlerConfigStatus
}