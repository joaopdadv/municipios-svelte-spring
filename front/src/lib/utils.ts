import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
	return twMerge(clsx(inputs));
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type WithoutChild<T> = T extends { child?: any } ? Omit<T, "child"> : T;
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type WithoutChildren<T> = T extends { children?: any } ? Omit<T, "children"> : T;
export type WithoutChildrenOrChild<T> = WithoutChildren<WithoutChild<T>>;
export type WithElementRef<T, U extends HTMLElement = HTMLElement> = T & { ref?: U | null };

// pagination/utils.ts ou pagination/index.ts

export type PaginationItem =
	| { type: 'page'; value: number; key: string }
	| { type: 'ellipsis'; key: string };


export function getPages({
	totalItems,
	itemsPerPage,
	currentPage,
	siblingCount = 1
}: {
	totalItems: number;
	itemsPerPage: number;
	currentPage: number;
	siblingCount: number;
}): PaginationItem[] {
	const totalPages = Math.ceil(totalItems / itemsPerPage);
	const pages: PaginationItem[] = [];

	const createPage = (num: number): PaginationItem => ({
		type: 'page',
		value: num,
		key: `page-${num}`
	});

	const DOTS: PaginationItem = { type: 'ellipsis', key: `ellipsis-${Math.random()}` };

	const startPage = Math.max(2, currentPage + 1 - siblingCount);
	const endPage = Math.min(totalPages - 1, currentPage + 1 + siblingCount);

	pages.push(createPage(1));

	if (startPage > 2) {
		pages.push(DOTS);
	}

	for (let i = startPage; i <= endPage; i++) {
		pages.push(createPage(i));
	}

	if (endPage < totalPages - 1) {
		pages.push(DOTS);
	}

	if (totalPages > 1) {
		pages.push(createPage(totalPages));
	}

	return pages;
}

