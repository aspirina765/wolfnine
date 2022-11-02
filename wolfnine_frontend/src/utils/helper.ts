

export const checkArrayHasIndex = (array: Array<any>, index: number) => {
    return typeof array[index] !== 'undefined';
}