import { createStore } from 'vuex'

// Create a new store instance.
const store = createStore({
    state () {
        return {
            userId: 0
        }
    },
    mutations: {
        setUserId (state, payload) {
            state.userId = payload
        }
    }
})

export default store