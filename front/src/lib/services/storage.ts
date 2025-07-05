export const storage = {
    set: (key: string, value: unknown) => {
        if (typeof localStorage !== 'undefined') {
            localStorage.setItem(key, JSON.stringify(value));
        }
    },

    get: <T = unknown>(key: string): T | null => {
        if (typeof localStorage !== 'undefined') {
            const value = localStorage.getItem(key);
            if (value !== null) {
                try {
                    return JSON.parse(value) as T;
                } catch {
                    return null;
                }
            }
        }
        return null;
    },

    remove: (key: string) => {
        if (typeof localStorage !== 'undefined') {
            localStorage.removeItem(key);
        }
    },

    clear: () => {
        if (typeof localStorage !== 'undefined') {
            localStorage.clear();
        }
    }
};
