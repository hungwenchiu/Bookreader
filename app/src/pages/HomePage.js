import React, {useState} from 'react';
import Layout from '../components/Layout'

const HomePage = () => (
  <Layout>
    <div>
        // For reference to get user, needs to be removed
        current user is : {sessionStorage.getItem("currentUser")}
    </div>
  </Layout>
)

export default HomePage;
