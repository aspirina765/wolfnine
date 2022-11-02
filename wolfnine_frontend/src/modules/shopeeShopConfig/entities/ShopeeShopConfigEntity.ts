
export declare type SaveShopeeShopConfig = {
    code: string,
    shopId: number
}

export declare type FindAllShopeeConfigByAuthUserParam = {
    limit: number,
    page: number,
    sortBy: string
}

export declare type GetShopeeCategoryListParams = {
    limit: number,
    page: number,
}


export declare type GetShopBrandListParams = {
    limit: number,
    page: number,
    shopeeCategoryId: number
}

export declare type PushItemsToShopeeShopRequest = {
    shopeeShopConfigId: number,
    logisticId: number,
    categoryId: number,
    productIds: Array<Number>,
    brandId: number,
    sellerStock: number
}