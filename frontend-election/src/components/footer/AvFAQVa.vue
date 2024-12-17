<script lang="ts">
import { ref } from 'vue';

export default {
  setup() {
    const openSection = ref<string | null>(null);

    const toggleSection = (section: string) => {
      openSection.value = openSection.value === section ? null : section;
    };

    return {
      openSection,
      toggleSection,
    };
  },
};
</script>

<template>
  <div class="d-flex flex-column min-vh-100">
    <div class="flex-grow-1">
      <div class="page-container">
        <h1>Explore NextGen Politics</h1>

        <!-- Terms and Conditions -->
        <section class="collapsible-section">
          <button class="collapsible" @click="toggleSection('terms')">📜 Terms and Conditions</button>
          <div v-if="openSection === 'terms'" class="content">
            <h3>Our Rules of Engagement</h3>
            <p>
              Welcome to NextGen Politics! By accessing our website, you agree to the following terms:
            </p>
            <ul>
              <li><strong>Use of Information:</strong> All materials on this platform are intended solely for informational purposes. Users should not replicate or distribute our content without explicit permission. Our content is developed carefully, and we ask that users respect intellectual property rights when sharing or referencing material found on NextGen Politics.</li>
              <li><strong>Data Privacy:</strong> We respect your privacy and follow strict guidelines to secure your personal data. Our commitment to privacy is unwavering, and we actively work to safeguard your information. This includes utilizing encryption, limiting access to sensitive data, and ensuring that only authorized personnel handle personal information. Please review our Privacy Policy for more comprehensive details on data handling, including what information we collect, why, and how it is stored securely.</li>
              <li><strong>Account Conduct:</strong> We maintain a respectful and inclusive environment where everyone is welcome to participate. Users are expected to engage responsibly, with no spam, harassment, or offensive language tolerated. Constructive feedback and positive interactions are encouraged, as they help foster a productive community. Any violation of conduct guidelines may result in suspension or removal of user access.</li>
              <li><strong>Third-Party Links:</strong> Our site may include links to third-party websites for additional resources. While we aim to ensure the reliability of these sources, we do not endorse or assume responsibility for the content or practices of external sites. Users are advised to review third-party site policies and exercise caution when sharing personal information on external sites.</li>
              <li><strong>Modifications:</strong> NextGen Politics reserves the right to update these terms as necessary to reflect changes in our services or policies. Any changes will take effect immediately upon posting. Users are encouraged to review this section regularly to stay informed about updates. Continued use of the platform signifies your acceptance of any modifications.</li>
            </ul>
            <p>By using our services, you agree to uphold these terms, engage in meaningful and respectful dialogue, and contribute positively to the NextGen Politics community. We aim to create a space where everyone feels welcome, informed, and empowered.</p>
          </div>
        </section>

        <!-- FAQ -->
        <section class="collapsible-section">
          <button class="collapsible" @click="toggleSection('faq')">❓ Frequently Asked Questions</button>
          <div v-if="openSection === 'faq'" class="content">
            <h3>Questions? We’ve Got Answers!</h3>
            <p>Here are some of the common questions about NextGen Politics and our platform:</p>
            <ul>
              <li><strong>What is NextGen Politics?</strong> NextGen Politics is an educational platform dedicated to making Dutch politics accessible, engaging, and informative for young people. We strive to break down complex political topics, provide unbiased information, and encourage thoughtful discussions on issues that matter.</li>
              <li><strong>How can I participate in NextGen Politics events?</strong> We host events throughout the year, including live debates, webinars, and Q&A sessions. You can join by following us on social media, subscribing to our newsletter, and checking our Events page regularly. Many events are free and open to everyone, while some may require early registration.</li>
              <li><strong>Is my personal information secure?</strong> Absolutely. NextGen Politics takes data privacy seriously. We employ advanced security protocols, including encryption, regular audits, and strict data access controls, to protect your personal information. Your data is used solely to improve your experience, and we never share it with third parties without consent. For additional details on data practices, refer to our comprehensive Privacy Policy.</li>
              <li><strong>How does NextGen Politics ensure unbiased information?</strong> Our editorial team comprises individuals with diverse perspectives who prioritize accuracy and neutrality. Every piece of content undergoes a thorough review process, with fact-checking and peer reviews to ensure that all information is reliable, up-to-date, and free from bias. Our mission is to provide an objective, well-rounded understanding of political issues.</li>
              <li><strong>Are there opportunities to volunteer?</strong> Yes! We offer various ways for passionate individuals to get involved, including content creation, community engagement, and event coordination. Volunteers play a critical role in our mission, and we welcome those who want to make a positive impact in the community. See our Job Openings section below for current opportunities to join our volunteer team.</li>
              <li><strong>What topics does NextGen Politics cover?</strong> Our platform covers a wide range of topics, including Dutch governance, political ideologies, current events, social issues, and environmental policies. We aim to provide insights into topics that resonate with young people and address the challenges and opportunities facing Dutch society today.</li>
              <li><strong>How can I suggest a topic or ask a question?</strong> We value feedback and suggestions from our community. If there’s a specific topic you’d like us to cover, or if you have a question, feel free to reach out via our Contact Us page. We aim to address all queries and incorporate user suggestions into our future content.</li>
            </ul>
            <p>If you have additional questions or need further assistance, don’t hesitate to contact us. We’re here to help make your experience on NextGen Politics as informative and engaging as possible.</p>
          </div>
        </section>

        <!-- Job Openings -->
        <section class="collapsible-section">
          <button class="collapsible" @click="toggleSection('jobs')">💼 Job Openings</button>
          <div v-if="openSection === 'jobs'" class="content">
            <h3>Join Our Team!</h3>
            <p>We’re always looking for passionate individuals who want to make an impact. Below are our current openings:</p>
            <ul>
              <li><strong>Content Writer:</strong> Craft engaging articles on Dutch politics, breaking down complex topics into accessible insights for young people. This role involves researching current events, collaborating with our editorial team, and creating content that empowers youth to engage with political issues.</li>
              <li><strong>Community Engagement Specialist:</strong> Foster a welcoming and dynamic community. This role involves organizing events, managing discussions, and ensuring that our members feel valued. Experience in event planning, community building, and outreach is beneficial, as you’ll be the bridge between our platform and our users.</li>
              <li><strong>Social Media Intern:</strong> Help grow our social presence by creating content for Instagram, Twitter, and TikTok. Ideal for students with a passion for social media and politics, this role offers the chance to learn social media strategies and contribute to NextGen Politics’ voice online.</li>
              <li><strong>Research Assistant:</strong> Support our content team by providing in-depth research on political trends and issues. This role involves fact-checking, gathering reliable sources, and assisting in the creation of informative resources. Strong analytical skills and a keen interest in Dutch politics are essential.</li>
            </ul>
            <p>To apply, please email your resume and a cover letter to <a href="mailto:careers@nextgenpolitics.nl">careers@nextgenpolitics.nl</a>. We look forward to hearing from you!</p>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 1rem;
}

.collapsible {
  background-color: #24786E99;
  color: #fff;
  font-size: 1.1rem;
  font-weight: bold;
  padding: 15px;
  border: none;
  width: 100%;
  text-align: left;
  outline: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-bottom: 0.5rem;
  border-radius: 8px;
}

.collapsible:hover {
  background-color: #24786E99;
}

.content {
  padding: 15px;
  background-color: #f4f4f4;
  display: block;
  overflow: hidden;
  transition: max-height 0.3s ease;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 1rem;
  color: #333;
}

h1 {
  padding: 20px;
}

h3 {
  color: #24786E99;
}

footer .container {
  max-width: 1000px;
}

footer h5 {
  color: #24786E99;
}

footer a {
  color: #666;
  text-decoration: none;
}

footer a:hover {
  text-decoration: underline;
  color: #24786E99;
}

@media (max-width: 768px) {
  .page-container {
    padding: 1rem 0.5rem;
  }

  h3 {
    font-size: 1.2rem;
  }

  .collapsible {
    font-size: 1rem;
  }

  footer h5 {
    font-size: 1rem;
  }
}
</style>
