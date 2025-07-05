// Client-side helpers (browser only)
export const browserCookies = {
    set: (name: string, value: string, options?: { days?: number }) => {
        if (typeof document === 'undefined') return;

        let cookie = `${name}=${encodeURIComponent(value)}; path=/`;

        if (options?.days) {
            const date = new Date();
            date.setTime(date.getTime() + options.days * 24 * 60 * 60 * 1000);
            cookie += `; expires=${date.toUTCString()}`;
        }

        document.cookie = cookie;
    },

    get: (name: string): string | null => {
        if (typeof document === 'undefined') return null;

        const match = document.cookie.match(new RegExp('(^| )' + name + '=([^;]+)'));
        return match ? decodeURIComponent(match[2]) : null;
    },

    remove: (name: string) => {
        browserCookies.set(name, '', { days: -1 });
    }
};
