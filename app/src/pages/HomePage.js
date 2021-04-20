import React, {useState} from 'react';
import Layout from '../components/Layout'

const HomePage = () => (
  <Layout>
    <div>
        current user is : {sessionStorage.getItem("currentUser")}
    </div>
  </Layout>
)

export default HomePage;