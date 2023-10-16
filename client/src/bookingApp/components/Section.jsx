const Section = ({ children, className }) => (
    <section
        className={`relative 
px-[5%] lg:px-[2%] pb-10
${className ? className : ""}`}
    >
        {children}
    </section>
);

export default Section;
