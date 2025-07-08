import { writable, derived } from 'svelte/store';

export interface PaginationState {
    currentPage: number;
    itemsPerPage: number;
    totalItems: number;
}

function createPaginationStore() {
    const { subscribe, update, set } = writable<PaginationState>({
        currentPage: 1,
        itemsPerPage: 15,
        totalItems: 0,
    });

    const totalPages = derived(
        { subscribe },
        ($store) => Math.ceil($store.totalItems / $store.itemsPerPage)
    );

    const pageData = derived(
        { subscribe },
        ($store) => {
            const startIndex = ($store.currentPage - 1) * $store.itemsPerPage;
            const endIndex = startIndex + $store.itemsPerPage;
            return { startIndex, endIndex };
        }
    );

    return {
        subscribe,
        set,

        totalPages,
        pageData,

        setPage: (page: number) => {
            update((store) => ({ ...store, currentPage: page }));
        },
        nextPage: () => {
            update((store) => {
                const maxPages = Math.ceil(store.totalItems / store.itemsPerPage);
                if (store.currentPage < maxPages) {
                    return { ...store, currentPage: store.currentPage + 1 };
                }
                return store;
            });
        },
        prevPage: () => {
            update((store) => {
                if (store.currentPage > 1) {
                    return { ...store, currentPage: store.currentPage - 1 };
                }
                return store;
            });
        },
        setItems: (totalItems: number, itemsPerPage: number) => {
            update((store) => ({
                ...store,
                totalItems,
                itemsPerPage,
                currentPage: 1,
            }));
        },
    };
}

export const paginationStore = createPaginationStore();